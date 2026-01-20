package com.samkarsa.prueba_backend.service;

public interface JwtService {
    String generateToken(String subject);
}
