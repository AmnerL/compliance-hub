package com.portafolio.compliancehub.report.domain.model.exceptions;

public class ReportGenerationException extends RuntimeException {
    public ReportGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
