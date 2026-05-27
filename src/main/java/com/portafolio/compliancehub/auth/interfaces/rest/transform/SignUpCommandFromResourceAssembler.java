package com.portafolio.compliancehub.auth.interfaces.rest.transform;

import com.portafolio.compliancehub.auth.domain.model.commands.SignUpCommand;
import com.portafolio.compliancehub.auth.interfaces.rest.resources.SignUpResource;

public class SignUpCommandFromResourceAssembler {

    public static SignUpCommand toCommandFromResource(SignUpResource resource) {
        return new SignUpCommand(
                resource.email(),
                resource.password(),
                resource.firstName(),
                resource.lastName(),
                resource.role());
    }
}