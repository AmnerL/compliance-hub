package com.portafolio.compliancehub.regulation.domain.model.exceptions;

public class VersionNotFoundException extends RuntimeException {
    public VersionNotFoundException(Long regulationId, Integer versionNumber) {
        super("Version " + versionNumber + " of regulation with ID " + regulationId + " not found.");
    }
}
