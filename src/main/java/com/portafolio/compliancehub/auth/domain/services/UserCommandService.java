package com.portafolio.compliancehub.auth.domain.services;

import com.portafolio.compliancehub.auth.application.dto.AuthenticatedUser;
import com.portafolio.compliancehub.auth.domain.model.commands.RefreshTokenCommand;
import com.portafolio.compliancehub.auth.domain.model.commands.SignInCommand;
import com.portafolio.compliancehub.auth.domain.model.commands.SignUpCommand;

import java.util.Optional;

public interface UserCommandService {
    Optional<AuthenticatedUser> handle(SignUpCommand command);

    Optional<AuthenticatedUser> handle(SignInCommand command);

    Optional<AuthenticatedUser> handle(RefreshTokenCommand command);
}
