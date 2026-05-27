package com.portafolio.compliancehub.auth.application.internal.outboundservices.tokens;

public interface TokenService {

    String generateToken(Long userId);

    String generateRefreshToken(Long userId);

    Long getUserIdFromToken(String token);

    boolean validateToken(String token);

    String refreshAccessToken(String refreshToken);
}
