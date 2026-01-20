package com.samkarsa.prueba_backend.service;

import com.samkarsa.prueba_backend.dto.request.LoginRequest;
import com.samkarsa.prueba_backend.dto.response.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
}
