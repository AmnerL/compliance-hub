package com.portafolio.compliancehub.auth.interfaces.rest.transform;

import com.portafolio.compliancehub.auth.application.dto.AuthenticatedUser;
import com.portafolio.compliancehub.auth.interfaces.rest.resources.AuthResource;

public class AuthResourceFromUserAssembler {
    public static AuthResource from(AuthenticatedUser authUser) {
        return new AuthResource(
                authUser.accessToken(),
                authUser.refreshToken(),
                authUser.user().getEmail(),
                authUser.user().getRole().name());
    }
}
