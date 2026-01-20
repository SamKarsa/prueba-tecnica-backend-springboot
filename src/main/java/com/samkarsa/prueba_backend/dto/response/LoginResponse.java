package com.samkarsa.prueba_backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginResponse(
        @Schema(example = "eyJhbGciOiJIUzM4NCJ9...", description = "JWT access token")
        String token,

        @Schema(example = "Bearer", description = "Token type for Authorization header")
        String tokenType
) {}


