package com.portafolio.compliancehub.auth.application.internal.commandservices;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portafolio.compliancehub.auth.application.dto.AuthenticatedUser;
import com.portafolio.compliancehub.auth.application.internal.outboundservices.hashing.HashingService;
import com.portafolio.compliancehub.auth.application.internal.outboundservices.tokens.TokenService;
import com.portafolio.compliancehub.auth.domain.model.aggregates.User;
import com.portafolio.compliancehub.auth.domain.model.commands.RefreshTokenCommand;
import com.portafolio.compliancehub.auth.domain.model.commands.SignInCommand;
import com.portafolio.compliancehub.auth.domain.model.commands.SignUpCommand;
import com.portafolio.compliancehub.auth.domain.model.exceptions.EmailAlreadyExistsException;
import com.portafolio.compliancehub.auth.domain.model.exceptions.InvalidCredentialsException;
import com.portafolio.compliancehub.auth.domain.model.exceptions.UserNotFoundException;
import com.portafolio.compliancehub.auth.domain.services.UserCommandService;
import com.portafolio.compliancehub.auth.infrastructure.jpa.repositories.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Transactional
@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;

    @Override
    public Optional<AuthenticatedUser> handle(SignUpCommand command) {
        if (userRepository.existsByEmail(command.email())) {
            throw new EmailAlreadyExistsException(command.email());
        }
        String hashedPassword = hashingService.encode(command.password());
        User user = User.create(command.email(), hashedPassword, command.firstName(), command.lastName(),
                command.role());
        User savedUser = userRepository.save(user);

        String accessToken = tokenService.generateToken(savedUser.getId());
        String refreshToken = tokenService.generateRefreshToken(savedUser.getId());
        return Optional.of(new AuthenticatedUser(savedUser, accessToken, refreshToken));
    }

    @Override
    public Optional<AuthenticatedUser> handle(SignInCommand command) {
        User user = userRepository.findByEmail(command.email())
                .orElseThrow(UserNotFoundException::new);
        if (!hashingService.matches(command.password(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }
        String accessToken = tokenService.generateToken(user.getId());
        String refreshToken = tokenService.generateRefreshToken(user.getId());
        return Optional.of(new AuthenticatedUser(user, accessToken, refreshToken));
    }

    @Override
    public Optional<AuthenticatedUser> handle(RefreshTokenCommand command) {
        String newAccessToken = tokenService.refreshAccessToken(command.refreshToken());
        Long userId = tokenService.getUserIdFromToken(command.refreshToken());
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        String newRefreshToken = tokenService.generateRefreshToken(user.getId());
        return Optional.of(new AuthenticatedUser(user, newAccessToken, newRefreshToken));
    }

}
