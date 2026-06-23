package com.portafolio.compliancehub.ai.domain.model.exceptions;

public class ConsultationNotFoundException extends RuntimeException {
    public ConsultationNotFoundException(Long consultationId) {
        super("Consultation with ID " + consultationId + " not found.");
    }
}
