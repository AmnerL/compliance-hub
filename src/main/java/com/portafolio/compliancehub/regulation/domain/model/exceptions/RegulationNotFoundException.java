package com.portafolio.compliancehub.regulation.domain.model.exceptions;

public class RegulationNotFoundException extends RuntimeException {
    public RegulationNotFoundException(Long regulationId) {
        super("Regulation with ID " + regulationId + " not found.");
    }
}
