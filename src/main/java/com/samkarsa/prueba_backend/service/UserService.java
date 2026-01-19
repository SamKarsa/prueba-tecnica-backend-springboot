package com.samkarsa.prueba_backend.service;

import com.samkarsa.prueba_backend.dto.request.RecoveryRequest;
import com.samkarsa.prueba_backend.dto.request.RegisterRequest;
import com.samkarsa.prueba_backend.dto.request.SetPasswordRequest;

public interface UserService {
    void register(RegisterRequest request);
    void setPassword(String token, SetPasswordRequest request);
    void requestRecovery(RecoveryRequest request);
    void resetPassword(String token, SetPasswordRequest request);
}
