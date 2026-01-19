package com.samkarsa.prueba_backend.service;

import com.samkarsa.prueba_backend.model.TokenType;
import com.samkarsa.prueba_backend.model.User;
import com.samkarsa.prueba_backend.model.UserToken;

public interface TokenService {
    UserToken createToken(User user, TokenType type, long ttlSeconds);
    UserToken getValidToken(String token, TokenType exceptedType);
    void markUsed(UserToken userToken);
}
