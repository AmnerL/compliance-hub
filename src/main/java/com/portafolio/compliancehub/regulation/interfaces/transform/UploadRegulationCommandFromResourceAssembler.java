package com.portafolio.compliancehub.regulation.interfaces.transform;

import org.springframework.web.multipart.MultipartFile;

import com.portafolio.compliancehub.regulation.domain.model.commands.UploadRegulationCommand;
import com.portafolio.compliancehub.regulation.interfaces.resource.UploadRegulationResource;

public class UploadRegulationCommandFromResourceAssembler {
    public static UploadRegulationCommand toCommandFromResource(
            UploadRegulationResource resource,
            MultipartFile file) {
        return new UploadRegulationCommand(
                resource.title(),
                resource.publicationDate(),
                resource.issuingEntity(),
                resource.category(),
                file);
    }

}
