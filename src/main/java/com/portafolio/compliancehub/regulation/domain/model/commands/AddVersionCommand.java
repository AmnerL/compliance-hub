package com.portafolio.compliancehub.regulation.domain.model.commands;

import org.springframework.web.multipart.MultipartFile;

public record AddVersionCommand(
        Long regulationId, MultipartFile file) {

}
