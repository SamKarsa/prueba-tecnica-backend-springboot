package com.samkarsa.prueba_backend.service.impl;

import com.samkarsa.prueba_backend.dto.request.LoginRequest;
import com.samkarsa.prueba_backend.dto.response.LoginResponse;
import com.samkarsa.prueba_backend.model.User;
import com.samkarsa.prueba_backend.repository.UserRepository;
import com.samkarsa.prueba_backend.service.AuthService;
import com.samkarsa.prueba_backend.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public LoginResponse login(LoginRequest request){
        String email = request.getEmail().trim().toLowerCase();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (Boolean.FALSE.equals(user.getActive())) {
            throw new RuntimeException("Invalid credentials");
        }

        boolean ok = passwordEncoder.matches(request.getPassword(), user.getPasswordHash());
        if (!ok) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getEmail());
        return new LoginResponse(token, "Bearer");
    }
}
