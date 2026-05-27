package com.portafolio.compliancehub.auth.domain.model.exceptions;

public class InvalidRefreshTokenException extends RuntimeException {
    public InvalidRefreshTokenException() {
        super("Invalid or expired refresh token.");
    }

    public InvalidRefreshTokenException(String message) {
        super(message);
    }
}
