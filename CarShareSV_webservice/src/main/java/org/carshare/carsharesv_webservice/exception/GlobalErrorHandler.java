package org.carshare.carsharesv_webservice.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.carshare.carsharesv_webservice.domain.dto.GenericResponse;
import org.carshare.carsharesv_webservice.util.ErrorsTool;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}
