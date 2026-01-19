package com.samkarsa.prueba_backend.service;

public interface MailService {
    void sendRegisterLink(String toEmail, String Link);
    void sendRecoveryLink(String toEmail, String Link);
}
