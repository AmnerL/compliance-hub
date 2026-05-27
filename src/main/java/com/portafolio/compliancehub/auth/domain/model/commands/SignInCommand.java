package com.portafolio.compliancehub.auth.domain.model.commands;

public record SignInCommand(String email, String password) {
    public static SignInCommand of(String email, String password) {
        return new SignInCommand(email, password);
    }
}
