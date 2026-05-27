package com.portafolio.compliancehub.auth.domain.model.commands;

import com.portafolio.compliancehub.auth.domain.model.valueobjects.RoleType;

public record SignUpCommand(
        String email,
        String password,
        String firstName,
        String lastName,
        RoleType role) {

    public static SignUpCommand of(String email, String password, String firstName, String lastName, RoleType role) {
        return new SignUpCommand(email, password, firstName, lastName, role);
    }
}
