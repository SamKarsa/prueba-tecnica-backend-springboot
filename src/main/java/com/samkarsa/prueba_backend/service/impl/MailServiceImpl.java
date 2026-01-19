package com.samkarsa.prueba_backend.service.impl;

import com.samkarsa.prueba_backend.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MailServiceImpl implements MailService {

    @Override
    public void sendRegisterLink(String toEmail, String link) {
        log.info("[MAIL-MOCK] Register link to {} -> {}", toEmail, link);
    }

    @Override
    public void sendRecoveryLink(String toEmail, String link) {
        log.info("[MAIL-MOCK] Recovery link to {} -> {}", toEmail, link);
    }
}
