package com.portafolio.compliancehub.auth.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenResource(
        @NotBlank String refreshToken) {
}
