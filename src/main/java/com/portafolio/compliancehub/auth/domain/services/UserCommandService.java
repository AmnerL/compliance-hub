package com.portafolio.compliancehub.auth.domain.services;

import com.portafolio.compliancehub.auth.application.dto.AuthenticatedUser;
import com.portafolio.compliancehub.auth.domain.model.commands.RefreshTokenCommand;
import com.portafolio.compliancehub.auth.domain.model.commands.SignInCommand;
import com.portafolio.compliancehub.auth.domain.model.commands.SignUpCommand;

public interface UserCommandService {
    AuthenticatedUser handle(SignUpCommand command);

    AuthenticatedUser handle(SignInCommand command);

    AuthenticatedUser handle(RefreshTokenCommand command);
}
