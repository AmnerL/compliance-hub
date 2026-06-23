package com.portafolio.compliancehub.admin.domain.service;

import java.util.List;
import com.portafolio.compliancehub.admin.infrastructure.logging.LogEntry;

public interface AdminLogService {
    List<LogEntry> getLogs(String method, String username);
}
