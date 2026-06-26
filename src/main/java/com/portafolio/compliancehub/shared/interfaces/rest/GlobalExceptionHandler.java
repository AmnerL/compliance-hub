package com.portafolio.compliancehub.shared.interfaces.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.portafolio.compliancehub.ai.domain.model.exceptions.ConsultationNotFoundException;
import com.portafolio.compliancehub.auth.domain.model.exceptions.EmailAlreadyExistsException;
import com.portafolio.compliancehub.auth.domain.model.exceptions.InvalidCredentialsException;
import com.portafolio.compliancehub.auth.domain.model.exceptions.InvalidRefreshTokenException;
import com.portafolio.compliancehub.checklist.domain.model.exceptions.ChecklistNotFoundException;
import com.portafolio.compliancehub.checklist.domain.model.exceptions.ItemNotFoundInChecklistException;
import com.portafolio.compliancehub.notification.domain.model.exceptions.NotificationNotFoundException;
import com.portafolio.compliancehub.regulation.domain.model.exceptions.NoActiveVersionException;
import com.portafolio.compliancehub.regulation.domain.model.exceptions.RegulationNotFoundException;
import com.portafolio.compliancehub.regulation.domain.model.exceptions.VersionNotFoundException;
import com.portafolio.compliancehub.report.domain.model.exceptions.ReportGenerationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public record ErrorResponse(String error) {}

    @ExceptionHandler({
        com.portafolio.compliancehub.auth.domain.model.exceptions.UserNotFoundException.class,
        com.portafolio.compliancehub.admin.domain.model.exceptions.UserNotFoundException.class,
        ChecklistNotFoundException.class,
        RegulationNotFoundException.class,
        VersionNotFoundException.class,
        ConsultationNotFoundException.class,
        NotificationNotFoundException.class,
        ItemNotFoundInChecklistException.class
    })
    public ResponseEntity<ErrorResponse> handleNotFoundExceptions(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleConflictExceptions(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler({
        InvalidCredentialsException.class,
        InvalidRefreshTokenException.class
    })
    public ResponseEntity<ErrorResponse> handleUnauthorizedExceptions(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(NoActiveVersionException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestExceptions(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<ErrorResponse> handleIllegalArgumentAndState(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(ReportGenerationException.class)
    public ResponseEntity<ErrorResponse> handleReportGeneration(ReportGenerationException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(ex.getMessage()));
    }
}
