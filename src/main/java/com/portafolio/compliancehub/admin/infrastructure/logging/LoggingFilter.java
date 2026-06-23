package com.portafolio.compliancehub.admin.infrastructure.logging;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoggingFilter extends OncePerRequestFilter {

    private final SystemLogBuffer logBuffer;

    public LoggingFilter(SystemLogBuffer logBuffer) {
        this.logBuffer = logBuffer;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        filterChain.doFilter(request, response);

        try {
            String method = request.getMethod();
            String uri = request.getRequestURI();
            String ip = request.getRemoteAddr();
            String username = "Anonymous";

            var authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()
                    && !"anonymousUser".equals(authentication.getPrincipal())) {
                username = authentication.getName();
            }

            int status = response.getStatus();
            String message = String.format("Request processed with status %d", status);

            LogEntry entry = new LogEntry(
                    LocalDateTime.now(),
                    method,
                    uri,
                    username,
                    ip,
                    message);

            logBuffer.addLog(entry);
        } catch (Exception e) {
            logger.error("Failed to log request in LoggingFilter", e);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/api/auth/") ||
                path.startsWith("/swagger-ui") ||
                path.startsWith("/v3/api-docs") ||
                path.equals("/error");
    }
}
