package com.portafolio.compliancehub.auth.interfaces.rest.transform;

import com.portafolio.compliancehub.auth.domain.model.commands.SignInCommand;
import com.portafolio.compliancehub.auth.interfaces.rest.resources.SignInResource;

public class SignInCommandFromResourceAssembler {
    public static SignInCommand toCommandFromResource(SignInResource resource) {
        return new SignInCommand(resource.email(), resource.password());
    }

}
