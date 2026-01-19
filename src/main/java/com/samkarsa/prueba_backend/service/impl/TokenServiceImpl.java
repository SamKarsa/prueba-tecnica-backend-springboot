package com.samkarsa.prueba_backend.service.impl;

import com.samkarsa.prueba_backend.model.TokenType;
import com.samkarsa.prueba_backend.model.User;
import com.samkarsa.prueba_backend.model.UserToken;
import com.samkarsa.prueba_backend.repository.UserTokenRepository;
import com.samkarsa.prueba_backend.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final UserTokenRepository userTokenRepository;

    @Override
    public UserToken createToken(User user, TokenType type, long ttlSeconds) {
        UserToken t = new UserToken();
        t.setUser(user);
        t.setType(type);
        t.setToken(UUID.randomUUID().toString());
        t.setExpiresAt(Instant.now().plusSeconds(ttlSeconds));
        t.setUsedAt(null);
        return userTokenRepository.save(t);
    }

    @Override
    public UserToken getValidToken(String token, TokenType expectedType) {
        UserToken t = userTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("invalid token"));

        if (t.getType() != expectedType) {
            throw new RuntimeException("Invalid token type");
        }
        if (t.getUsedAt() != null) {
            throw new RuntimeException("Token already used");
        }
        if (t.getExpiresAt().isBefore(Instant.now())){
            throw new RuntimeException("Token expired");
        }
        return t;
    }

    @Override
    public void markUsed(UserToken userToken) {
        userTokenRepository.save(userToken);
    }
}
