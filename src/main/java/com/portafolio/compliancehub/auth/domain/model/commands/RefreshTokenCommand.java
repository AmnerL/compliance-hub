package com.portafolio.compliancehub.auth.domain.model.commands;

public record RefreshTokenCommand(String refreshToken) {
    public static RefreshTokenCommand of(String refreshToken) {
        return new RefreshTokenCommand(refreshToken);
    }
}
