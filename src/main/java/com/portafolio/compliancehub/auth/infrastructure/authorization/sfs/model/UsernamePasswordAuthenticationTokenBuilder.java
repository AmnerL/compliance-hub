package com.portafolio.compliancehub.auth.infrastructure.authorization.sfs.model;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import jakarta.servlet.http.HttpServletRequest;

public class UsernamePasswordAuthenticationTokenBuilder {

    public static UsernamePasswordAuthenticationToken build(UserDetails principal,
            HttpServletRequest request) {
        var token = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return token;
    }
}
