package com.portafolio.compliancehub.regulation.interfaces.resource;

import java.time.LocalDateTime;

public record VersionResponseResource(
        Long id,
        Integer versionNumber,
        String fileName,
        Long fileSize,
        LocalDateTime uploadDate,
        Boolean active) {

}
