package com.samkarsa.prueba_backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record MessageResponse (
        @Schema(example = "User registered. Check email for password setup link")
        String message
) {}
