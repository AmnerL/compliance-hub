package com.portafolio.compliancehub.admin.interfaces.rest.transform;

import com.portafolio.compliancehub.admin.infrastructure.logging.LogEntry;
import com.portafolio.compliancehub.admin.interfaces.rest.resources.LogEntryResponse;

public class LogEntryResponseFromLogEntryAssembler {
    public static LogEntryResponse toResourceFromValueObject(LogEntry logEntry) {
        return new LogEntryResponse(
            logEntry.timestamp(),
            logEntry.method(),
            logEntry.uri(),
            logEntry.username(),
            logEntry.ip(),
            logEntry.message()
        );
    }
}
