package com.portafolio.compliancehub.admin.interfaces.rest.resources;

import java.time.LocalDateTime;

public record UserAdminResponse(
    Long id,
    String email,
    String firstName,
    String lastName,
    String role,
    Boolean active,
    LocalDateTime createdAt
) {}
