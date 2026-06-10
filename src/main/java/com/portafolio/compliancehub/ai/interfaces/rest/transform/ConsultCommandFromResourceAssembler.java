package com.portafolio.compliancehub.ai.interfaces.rest.transform;

import com.portafolio.compliancehub.ai.domain.model.commands.ConsultCommand;
import com.portafolio.compliancehub.ai.interfaces.rest.resources.ConsultRequestResource;

public class ConsultCommandFromResourceAssembler {
    public static ConsultCommand toCommandFromResource(Long userId, ConsultRequestResource resource) {
        return new ConsultCommand(userId, resource.question());
    }
}
