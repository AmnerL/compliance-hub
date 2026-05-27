package com.portafolio.compliancehub.auth.interfaces.rest.resources;

import com.portafolio.compliancehub.auth.domain.model.valueobjects.RoleType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignUpResource(
        @NotBlank @Email String email,
        @NotBlank String password,
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotNull RoleType role) {
}
