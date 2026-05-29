package com.portafolio.compliancehub.auth.interfaces.rest;

import com.portafolio.compliancehub.auth.application.dto.AuthenticatedUser;
import com.portafolio.compliancehub.auth.domain.model.commands.RefreshTokenCommand;
import com.portafolio.compliancehub.auth.domain.model.commands.SignInCommand;
import com.portafolio.compliancehub.auth.domain.model.commands.SignUpCommand;
import com.portafolio.compliancehub.auth.domain.services.UserCommandService;
import com.portafolio.compliancehub.auth.interfaces.rest.resources.*;
import com.portafolio.compliancehub.auth.interfaces.rest.transform.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserCommandService userCommandService;

    @PostMapping("/register")
    public ResponseEntity<AuthResource> register(@Valid @RequestBody SignUpResource resource) {
        SignUpCommand command = SignUpCommandFromResourceAssembler.toCommandFromResource(resource);
        AuthenticatedUser authUser = userCommandService.handle(command);
        AuthResource authResource = AuthResourceFromUserAssembler.from(authUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(authResource);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResource> login(@Valid @RequestBody SignInResource resource) {
        SignInCommand command = SignInCommandFromResourceAssembler.toCommandFromResource(resource);
        AuthenticatedUser authUser = userCommandService.handle(command);
        AuthResource authResource = AuthResourceFromUserAssembler.from(authUser);
        return ResponseEntity.ok(authResource);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResource> refresh(@Valid @RequestBody RefreshTokenResource resource) {
        RefreshTokenCommand command = RefreshTokenCommandFromResourceAssembler.toCommandFromResource(resource);
        AuthenticatedUser authUser = userCommandService.handle(command);
        AuthResource authResource = AuthResourceFromUserAssembler.from(authUser);
        return ResponseEntity.ok(authResource);
    }
}