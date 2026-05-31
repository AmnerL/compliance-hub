package com.portafolio.compliancehub.checklist.domain.model.exceptions;

public class ChecklistNotFoundException extends RuntimeException {
    public ChecklistNotFoundException(Long checklistId) {
        super("Checklist with ID " + checklistId + " not found.");
    }
}
