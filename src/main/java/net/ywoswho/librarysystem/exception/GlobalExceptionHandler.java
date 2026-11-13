package net.ywoswho.librarysystem.exception;

import jakarta.persistence.EntityNotFoundException;
import net.ywoswho.librarysystem.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(
            EntityNotFoundException e
    ) {
        var error = new ErrorResponse(
                "Not found",
                e.getMessage(),
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalStateException(
            IllegalStateException e
    ) {
        var error = new ErrorResponse(
                "Conflict",
                e.getMessage(),
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleRentalOwnershipException(
            AccessDeniedException e
    ) {
        var error = new ErrorResponse(
                "Forbidden",
                e.getMessage(),
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
        );

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(error);
    }
}
