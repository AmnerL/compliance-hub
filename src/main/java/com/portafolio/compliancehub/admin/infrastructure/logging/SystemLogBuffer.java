package com.portafolio.compliancehub.admin.infrastructure.logging;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.springframework.stereotype.Component;

@Component
public class SystemLogBuffer {
    private static final int MAX_LOGS = 500;
    private final Queue<LogEntry> logs = new ConcurrentLinkedQueue<>();

    public void addLog(LogEntry entry) {
        logs.add(entry);
        while (logs.size() > MAX_LOGS) {
            logs.poll();
        }
    }

    public Queue<LogEntry> getLogs() {
        return logs;
    }
}
