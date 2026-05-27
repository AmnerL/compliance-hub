package com.portafolio.compliancehub.auth.interfaces.rest.transform;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.portafolio.compliancehub.auth.domain.model.aggregates.User;
import com.portafolio.compliancehub.auth.interfaces.rest.resources.AuthResource;

public class AuthResourceFromPairAssembler {
    public static AuthResource fromPair(ImmutablePair<User, String> pair, String refreshToken) {
        User user = pair.left;
        String accessToken = pair.right;
        return new AuthResource(
                accessToken,
                refreshToken,
                user.getEmail(),
                user.getRole().name());
    }

}
