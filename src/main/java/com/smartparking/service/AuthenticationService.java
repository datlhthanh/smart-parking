package com.smartparking.service;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.smartparking.dto.request.*;
import com.smartparking.dto.response.IntrospectResponse;
import com.smartparking.dto.response.LoginResponse;
import com.smartparking.dto.response.RegisterResponse;
import com.smartparking.entity.InvalidatedToken;
import com.smartparking.entity.OtpToken;
import com.smartparking.entity.Role;
import com.smartparking.entity.User;
import com.smartparking.enums.ErrorCode;
import com.smartparking.enums.RoleName;
import com.smartparking.enums.UserStatus;
import com.smartparking.exception.AppException;
import com.smartparking.mapper.UserMapper;
import com.smartparking.repository.*;
import com.smartparking.service.validator.UserValidator;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    OtpTokenRepository otpTokenRepository;
    InvalidatedRepository invalidatedRepository;

    UserValidator userValidator;
    EmailService emailService;

    UserMapper userMapper;

    PasswordEncoder passwordEncoder;

    @NonFinal
    @Value("${jwt.signerKey}")
    String SIGNER_KEY;

    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        // kiểm tra tổn tại: email, phone number
        userValidator.checkEmailAndPhoneExists(request.getEmail(), request.getPhoneNumber());

        // tim role USER trong danh sách role
        Role userRole = roleRepository
                .findByName(RoleName.USER.name())
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        // dùng mapper để tạo user mới, set các trường từ request
        User user = userMapper.toRegisterUser(request);

        // set status mặc định là ACTIVE, set role là USER
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStatus(UserStatus.ACTIVE);
        user.setRoles(Set.of(userRole));

        // lưu xuống DB
        return userMapper.toRegisterResponse(userRepository.save(user));
    }

    public LoginResponse login(LoginRequest request) {
        // tìm user theo email
        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_CREDENTIALS));

        // kiểm tra password có khớp không
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.INVALID_CREDENTIALS);
        }

        var token = generateToken(user);

        // trả về token
        return LoginResponse.builder().token(token).authenticated(true).build();
    }

    private String generateToken(User user) {
        // tạo header
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        Instant expiration = Instant.now().plus(30, ChronoUnit.MINUTES);

        // thêm các field vào claim set
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issuer("smart-parking")
                .issueTime(new Date())
                .expirationTime(new Date(expiration.toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("roles", roleScope(user))
                .build();

        // nhúng claim set vào payload
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            // ký với SIGNER_KEY
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));

            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    private String roleScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles()))
            user.getRoles().forEach(role -> stringJoiner.add(role.getName()));

        return stringJoiner.toString();
    }

    public IntrospectResponse introspect(IntrospectRequest request) {
        var token = request.getToken();

        var isValid = true;

        try {
            verifyToken(token);
        } catch (Exception e) {
            isValid = false;
        }

        return IntrospectResponse.builder().validated(isValid).build();
    }

    private SignedJWT verifyToken(String token) throws JOSEException, ParseException {
        // tạo JWSVerifier với SIGNER_KEY
        JWSVerifier jwsVerifier = new MACVerifier(SIGNER_KEY.getBytes());

        // parse token thành SignedJWT
        SignedJWT signedJWT = SignedJWT.parse(token);

        // xác thực token: verify signature
        var verified = signedJWT.verify(jwsVerifier);

        // lấy ra expiration time từ claim set để kiểm tra token đã hết hạn chưa
        var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        // nếu token không hợp lệ hoặc đã hết hạn thì ném exception
        if (!(verified && expiryTime.after(new Date()))) throw new AppException(ErrorCode.UNAUTHENTICATED);

        // kiểm tra xem token này có trong danh sách bị vô hiệu hóa không (logout)
        if (invalidatedRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return signedJWT;
    }

    @Transactional
    public void forgotPassword(ForgotPasswordRequest request) {
        // tìm User
        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // tạo mã OTP ngẫu nhiên 6 số
        String otp = String.format("%06d", new Random().nextInt(999999));

        // lưu vào DB (hạn là 5 phút)
        OtpToken token = OtpToken.builder()
                .otp(otp)
                .user(user)
                .expiryTime(LocalDateTime.now().plusMinutes(5))
                .build();
        otpTokenRepository.save(token);

        // gửi Email
        emailService.sendOtpEmail(user.getEmail(), otp);
    }

    @Transactional
    public void resetPassword(ResetPasswordRequest request) {
        // tìm User
        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // tìm Token xem có khớp với User và OTP người dùng nhập không
        OtpToken token = otpTokenRepository
                .findByUserAndOtp(user, request.getOtp())
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_OTP));

        // kiểm tra xem mã đã hết hạn chưa
        if (token.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new AppException(ErrorCode.EXPIRED_OTP);
        }

        // update mật khẩu mới
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        // xóa mã đó đi (để không bị dùng lại)
        otpTokenRepository.delete(token);
    }

    @Transactional
    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        // xác thực token (nếu token không hợp lệ sẽ ném exception)
        var signToken = verifyToken(request.getToken());

        // lấy JWT ID (jit) và expiration time từ token
        String jit = signToken.getJWTClaimsSet().getJWTID();
        Date exp = signToken.getJWTClaimsSet().getExpirationTime();

        // tạo một bản ghi mới đánh dấu token này là "đã bị vô hiệu hóa"
        InvalidatedToken invalidatedToken =
                InvalidatedToken.builder().id(jit).expiryTime(exp).build();

        // lưu token này vào cơ sở dữ liệu
        // khi có request mới hệ thống sẽ kiểm tra xem token này có trong danh sách bị vô hiệu hóa không
        invalidatedRepository.save(invalidatedToken);
    }
}
