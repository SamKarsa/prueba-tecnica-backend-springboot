package com.samkarsa.prueba_backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @Schema(example = "test@mail.com", description = "User email")
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 255)
    private String email;

    @Schema(
            description = "Full name of the user",
            example = "Juan Pérez",
            maxLength = 100
    )
    @NotBlank(message = "Full name is required")
    @Size(max = 100)
    private String fullName;
}
