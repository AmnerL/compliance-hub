package com.portafolio.compliancehub.regulation.domain.model.exceptions;

public class NoActiveVersionException extends RuntimeException {
    public NoActiveVersionException(Long regulationId) {
        super("Regulation with ID " + regulationId + " has no active version.");
    }
}
