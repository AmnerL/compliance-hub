package com.portafolio.compliancehub.auth.interfaces.rest.transform;

import com.portafolio.compliancehub.auth.domain.model.commands.RefreshTokenCommand;
import com.portafolio.compliancehub.auth.interfaces.rest.resources.RefreshTokenResource;

public record RefreshTokenCommandFromResourceAssembler() {
    public static RefreshTokenCommand toCommandFromResource(RefreshTokenResource resource) {
        return new RefreshTokenCommand(resource.refreshToken());
    }

}
