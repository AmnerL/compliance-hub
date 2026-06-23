package com.portafolio.compliancehub.admin.interfaces.rest.resources;

import java.time.LocalDateTime;

public record LogEntryResponse(
    LocalDateTime timestamp,
    String method,
    String uri,
    String username,
    String ip,
    String message
) {}
