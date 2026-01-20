package com.samkarsa.prueba_backend.service.impl;

import com.samkarsa.prueba_backend.dto.request.RecoveryRequest;
import com.samkarsa.prueba_backend.dto.request.RegisterRequest;
import com.samkarsa.prueba_backend.dto.request.SetPasswordRequest;
import com.samkarsa.prueba_backend.model.TokenType;
import com.samkarsa.prueba_backend.model.User;
import com.samkarsa.prueba_backend.model.UserToken;
import com.samkarsa.prueba_backend.repository.UserRepository;
import com.samkarsa.prueba_backend.service.MailService;
import com.samkarsa.prueba_backend.service.TokenService;
import com.samkarsa.prueba_backend.service.UserService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    @Override
    public void register(RegisterRequest request) {
        String email = request.getEmail().trim().toLowerCase();

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setEmail(email);
        user.setFullName(request.getFullName().trim());
        user.setActive(false);
        user.setCreatedAt(Instant.now());

        //Contraseña hasheada para que cumpla con el parametro null y el usuario queda inactivo
        user.setPasswordHash(passwordEncoder.encode("TEMP_" + UUID.randomUUID()));
        user = userRepository.save(user);

        UserToken token = tokenService.createToken(user, TokenType.REGISTER, 24 * 3600); // 24h

        String link = baseUrl + "/set-password.html?token=" + token.getToken();
        mailService.sendRegisterLink(user.getEmail(), link);
    }

    @Override
    public void setPassword(String token, SetPasswordRequest request) {
        UserToken userToken = tokenService.getValidToken(token, TokenType.REGISTER);

        User user= userToken.getUser();
        user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        user.setActive(true);
        userRepository.save(user);

        userToken.setUsedAt(Instant.now());
        tokenService.markUsed(userToken);
    }

    @Override
    public void requestRecovery(RecoveryRequest request) {
        String email = request.getEmail().trim().toLowerCase();

        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) return;

        UserToken token = tokenService.createToken(user, TokenType.RECOVERY,30 * 60); // 30 min

        String link = baseUrl + "/set-password.html?token=" + token.getToken() + "&mode=recovery";
        mailService.sendRecoveryLink(user.getEmail(), link);
    }

    @Override
    public void resetPassword(String token, SetPasswordRequest request) {
        UserToken userToken = tokenService.getValidToken(token, TokenType.RECOVERY);

        User user = userToken.getUser();
        user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        userToken.setUsedAt(Instant.now());
        tokenService.markUsed(userToken);
    }
}
