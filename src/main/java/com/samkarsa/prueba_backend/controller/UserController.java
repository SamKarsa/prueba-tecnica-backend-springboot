package com.samkarsa.prueba_backend.controller;

import com.samkarsa.prueba_backend.dto.request.RecoveryRequest;
import com.samkarsa.prueba_backend.dto.request.RegisterRequest;
import com.samkarsa.prueba_backend.dto.request.SetPasswordRequest;
import com.samkarsa.prueba_backend.dto.response.MessageResponse;
import com.samkarsa.prueba_backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(@Valid @RequestBody RegisterRequest request) {
        userService.register(request);
        return ResponseEntity.ok(new MessageResponse("User registered. Check email for password setup link"));
    }

    @PostMapping("/set-password")
    public ResponseEntity<MessageResponse> setPassword(@RequestParam("token") String token, @Valid @RequestBody SetPasswordRequest request) {
        userService.setPassword(token, request);
        return ResponseEntity.ok(new MessageResponse("Password set successfully. User is now active"));
    }

    @PostMapping("/recovery-request")
    public ResponseEntity<MessageResponse> recoveryRequest(@Valid @RequestBody RecoveryRequest request){
        userService.requestRecovery(request);

        return ResponseEntity.ok(new MessageResponse("If the email exists, a recovery link was sent."));
    }

    @PostMapping("/recovery-set-password")
    public ResponseEntity<MessageResponse> recoverySetPassword(@RequestParam("token") String token, @Valid @RequestBody SetPasswordRequest request) {
        userService.resetPassword(token, request);
        return ResponseEntity.ok(new MessageResponse("Password reset successfully."));
    }
}
