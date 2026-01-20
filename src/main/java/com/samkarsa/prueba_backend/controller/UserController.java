package com.samkarsa.prueba_backend.controller;

import com.samkarsa.prueba_backend.dto.request.RecoveryRequest;
import com.samkarsa.prueba_backend.dto.request.RegisterRequest;
import com.samkarsa.prueba_backend.dto.request.SetPasswordRequest;
import com.samkarsa.prueba_backend.dto.response.MessageResponse;
import com.samkarsa.prueba_backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Users", description = "User registration, password setup and recovery")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(
            summary = "Register user",
            description = "Creates a new inactive user and sends an email with a personalized token link to set the password."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User registered",
                    content = @Content(schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation error", content = @Content),
            @ApiResponse(responseCode = "409", description = "Email already registered", content = @Content)
    })
    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<MessageResponse> register(@Valid @RequestBody RegisterRequest request) {
        userService.register(request);
        return ResponseEntity.ok(new MessageResponse("User registered. Check email for password setup link"));
    }

    @Operation(
            summary = "Set password using registration token",
            description = "Sets a new password using only the token received by email. The token cannot be used twice."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Password set successfully",
                    content = @Content(schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation error", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid/expired token", content = @Content),
            @ApiResponse(responseCode = "409", description = "Token already used", content = @Content)
    })
    @PostMapping(value = "/set-password", consumes = "application/json", produces = "application/json")
    public ResponseEntity<MessageResponse> setPassword(@RequestParam("token") String token, @Valid @RequestBody SetPasswordRequest request) {
        userService.setPassword(token, request);
        return ResponseEntity.ok(new MessageResponse("Password set successfully. User is now active"));
    }

    @Operation(
            summary = "Request password recovery",
            description = "Sends a recovery email with a personalized token link. Response is generic even if the email doesn't exist (prevents user enumeration)."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Recovery email requested",
                    content = @Content(schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation error", content = @Content)
    })
    @PostMapping(value = "/recovery-request", consumes = "application/json", produces = "application/json")
    public ResponseEntity<MessageResponse> recoveryRequest(@Valid @RequestBody RecoveryRequest request){
        userService.requestRecovery(request);

        return ResponseEntity.ok(new MessageResponse("If the email exists, a recovery link was sent."));
    }

    @Operation(
            summary = "Reset password using recovery token",
            description = "Resets the password using the recovery token received by email. The token cannot be reused."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Password reset successfully",
                    content = @Content(schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation error", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid/expired token", content = @Content),
            @ApiResponse(responseCode = "409", description = "Token already used", content = @Content)
    })
    @PostMapping(value = "/recovery-set-password", consumes = "application/json", produces = "application/json")
    public ResponseEntity<MessageResponse> recoverySetPassword(@RequestParam("token") String token, @Valid @RequestBody SetPasswordRequest request) {
        userService.resetPassword(token, request);
        return ResponseEntity.ok(new MessageResponse("Password reset successfully."));
    }

    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Check authentication",
            description = "Protected endpoint to verify JWT authentication is working."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Authenticated",
                    content = @Content(schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "401", description = "Missing/invalid JWT", content = @Content)
    })
    @GetMapping(value = "/me", produces = "application/json")
    public ResponseEntity<MessageResponse> me() {
        return ResponseEntity.ok(new MessageResponse("JWT OK - authenticated"));
    }

}
