package com.portafolio.compliancehub.auth.interfaces.rest.resources;

public record AuthResource(
        String accessToken,
        String refreshToken,
        String email,
        String role) {

}
