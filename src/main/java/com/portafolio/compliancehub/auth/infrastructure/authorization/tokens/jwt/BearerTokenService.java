package com.portafolio.compliancehub.auth.infrastructure.authorization.tokens.jwt;

import com.portafolio.compliancehub.auth.application.internal.outboundservices.tokens.TokenService;

import jakarta.servlet.http.HttpServletRequest;

public interface BearerTokenService extends TokenService {
    String getBearerTokenFrom(HttpServletRequest request);
}
