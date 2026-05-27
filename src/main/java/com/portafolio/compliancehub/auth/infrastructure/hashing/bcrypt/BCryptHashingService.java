package com.portafolio.compliancehub.auth.infrastructure.hashing.bcrypt;

import com.portafolio.compliancehub.auth.application.internal.outboundservices.hashing.HashingService;

import org.springframework.security.crypto.password.PasswordEncoder;

public interface BCryptHashingService extends HashingService, PasswordEncoder {

}
