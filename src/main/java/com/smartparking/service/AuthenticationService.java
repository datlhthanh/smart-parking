package com.smartparking.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.smartparking.dto.request.*;
import com.smartparking.dto.response.IntrospectResponse;
import com.smartparking.dto.response.LoginResponse;
import com.smartparking.dto.response.RegisterResponse;
import com.smartparking.entity.ForgotPasswordToken;
import com.smartparking.entity.Role;
import com.smartparking.entity.User;
import com.smartparking.enums.ErrorCode;
import com.smartparking.enums.RoleName;
import com.smartparking.enums.UserStatus;
import com.smartparking.exception.AppException;
import com.smartparking.mapper.UserMapper;
import com.smartparking.repository.ForgotPasswordTokenRepository;
import com.smartparking.repository.RoleRepository;
import com.smartparking.repository.UserRepository;
import com.smartparking.service.validator.UserValidator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Random;
import java.util.Set;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    ForgotPasswordTokenRepository forgotPasswordTokenRepository;

    UserValidator userValidator;
    EmailService emailService;

    UserMapper userMapper;

    PasswordEncoder passwordEncoder;

    @NonFinal
    @Value("${jwt.signerKey}")
    String SIGNER_KEY;

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

    private void verifyToken(String token) throws JOSEException, ParseException {
        JWSVerifier jwsVerifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        var verified = signedJWT.verify(jwsVerifier);

        var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        if (!(verified && expiryTime.after(new Date()))) throw new AppException(ErrorCode.UNAUTHENTICATED);
    }

    public void forgotPassword(ForgotPasswordRequest request) {
        // 1. tìm User
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // 2. tạo mã OTP ngẫu nhiên 6 số
        String otp = String.format("%06d", new Random().nextInt(999999));

        // 3. lưu vào DB (hạn là 5 phút)
        ForgotPasswordToken token = ForgotPasswordToken.builder()
                .otp(otp)
                .user(user)
                .expiryTime(LocalDateTime.now().plusMinutes(5))
                .build();
        forgotPasswordTokenRepository.save(token);

        // 4. gửi Email
        emailService.sendOtpEmail(user.getEmail(), otp);
    }

    public void resetPassword(ResetPasswordRequest request) {
        // 1. tìm User
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // 2. tìm Token xem có khớp với User và OTP người dùng nhập không
        ForgotPasswordToken token = forgotPasswordTokenRepository
                .findByUserAndOtp(user, request.getOtp())
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_OTP));

        // 3. kiểm tra xem mã đã hết hạn chưa
        if (token.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new AppException(ErrorCode.EXPIRED_OTP);
        }

        // 4. update mật khẩu mới
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        // 5. xóa mã đó đi (để không bị dùng lại)
        forgotPasswordTokenRepository.delete(token);
    }
}
