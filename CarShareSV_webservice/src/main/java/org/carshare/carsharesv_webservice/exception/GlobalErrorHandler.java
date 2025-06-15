package org.carshare.carsharesv_webservice.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.carshare.carsharesv_webservice.domain.dto.GenericResponse;
import org.carshare.carsharesv_webservice.util.ErrorsTool;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
public class GlobalErrorHandler {
    private final ErrorsTool errorsTool;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponse> GeneralHandler(Exception ex) {
        log.error(ex.getMessage());
        log.error(ex.getClass().getCanonicalName());

        return GenericResponse.builder()
                .data(ex.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build().buildResponse();
    }

    @ExceptionHandler(ExistingUserException.class)
    public ResponseEntity<GenericResponse> handleExistingUserException(ExistingUserException ex, WebRequest request) {

        CustomErrorResponse errorResponse = new CustomErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false)
        );

        return GenericResponse.builder()
                .data(errorResponse)
                .status(HttpStatus.CONFLICT)
                .build().buildResponse();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<GenericResponse> HandleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {

        CustomErrorResponse errorResponse = new CustomErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false)
        );

        return GenericResponse.builder()
                .data(errorResponse)
                .status(HttpStatus.NOT_FOUND)
                .build().buildResponse();
    }

    @ExceptionHandler(NotActiveUserException.class)
    public ResponseEntity<GenericResponse> HandleNotActiveUserException(NotActiveUserException ex, WebRequest request) {

        CustomErrorResponse errorResponse = new CustomErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false)
        );

        return GenericResponse.builder()
                .data(errorResponse)
                .status(HttpStatus.NOT_FOUND)
                .build().buildResponse();
    }

    @ExceptionHandler(NotAllowedOperationException.class)
    public ResponseEntity<GenericResponse> HandleNotAllowedOperationException(NotAllowedOperationException ex, WebRequest request) {

        CustomErrorResponse errorResponse = new CustomErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false)
        );

        return GenericResponse.builder()
                .data(errorResponse)
                .status(HttpStatus.FORBIDDEN)
                .build().buildResponse();
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<GenericResponse> HandleAccessDeniedException(AccessDeniedException ex, WebRequest request) {

        CustomErrorResponse errorResponse = new CustomErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false)
        );

        return GenericResponse.builder()
                .data(errorResponse)
                .status(HttpStatus.FORBIDDEN)
                .build().buildResponse();
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<GenericResponse> HandleUsernameNotFoundException(UsernameNotFoundException ex, WebRequest request) {

        CustomErrorResponse errorResponse = new CustomErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false)
        );

        return GenericResponse.builder()
                .data(errorResponse)
                .status(HttpStatus.NOT_FOUND)
                .build().buildResponse();
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<GenericResponse> HandleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex, WebRequest request) {

        CustomErrorResponse errorResponse = new CustomErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false)
        );

        return GenericResponse.builder()
                .data(errorResponse)
                .status(HttpStatus.CONFLICT)
                .build().buildResponse();
    }

    @ExceptionHandler(RequiresReauthenticationException.class)
    public ResponseEntity<GenericResponse> HandleRequiresReauthenticationException(RequiresReauthenticationException ex, WebRequest request) {

        CustomErrorResponse errorResponse = new CustomErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false)
        );

        return GenericResponse.builder()
                .data(errorResponse)
                .status(HttpStatus.UNAUTHORIZED)
                .build().buildResponse();
    }
}
