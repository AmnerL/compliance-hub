package com.portafolio.compliancehub.admin.application.internal;

import java.util.List;

import org.springframework.stereotype.Service;

import com.portafolio.compliancehub.admin.domain.service.AdminLogService;
import com.portafolio.compliancehub.admin.infrastructure.logging.LogEntry;
import com.portafolio.compliancehub.admin.infrastructure.logging.SystemLogBuffer;

@Service
public class AdminLogServiceImpl implements AdminLogService {

    private final SystemLogBuffer logBuffer;

    public AdminLogServiceImpl(SystemLogBuffer logBuffer) {
        this.logBuffer = logBuffer;
    }

    @Override
    public List<LogEntry> getLogs(String method, String username) {
        return logBuffer.getLogs().stream()
                .filter(log -> method == null || log.method().equalsIgnoreCase(method))
                .filter(log -> username == null || log.username().equalsIgnoreCase(username))
                .toList();
    }
}
