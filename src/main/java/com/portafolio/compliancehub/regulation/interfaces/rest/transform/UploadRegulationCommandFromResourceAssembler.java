package com.portafolio.compliancehub.regulation.interfaces.rest.transform;

import org.springframework.web.multipart.MultipartFile;

import com.portafolio.compliancehub.regulation.domain.model.commands.UploadRegulationCommand;
import com.portafolio.compliancehub.regulation.interfaces.rest.resource.UploadRegulationResource;

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
