package com.portafolio.compliancehub.auth.application.dto;

import com.portafolio.compliancehub.auth.domain.model.aggregates.User;

public record AuthenticatedUser(User user, String accessToken, String refreshToken) {

}
