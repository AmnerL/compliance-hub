package com.portafolio.compliancehub.auth.domain.model.exceptions;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Invalid email or password.");
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
