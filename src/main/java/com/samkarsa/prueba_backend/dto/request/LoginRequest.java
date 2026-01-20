package com.samkarsa.prueba_backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {
    @Schema(example = "test@mail.com", description = "User email")
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 255)
    private String email;

    @Schema(example = "Temporal1234", description = "User password")
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 100)
    private String password;
}
