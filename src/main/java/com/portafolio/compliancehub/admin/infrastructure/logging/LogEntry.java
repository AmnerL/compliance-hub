package com.portafolio.compliancehub.admin.infrastructure.logging;

import java.time.LocalDateTime;

public record LogEntry(
    LocalDateTime timestamp,
    String method,
    String uri,
    String username,
    String ip,
    String message
) {}
