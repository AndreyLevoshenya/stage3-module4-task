package com.mjc.school.controller.exceptions;

import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.exceptions.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ValidationException.class})
    public ResponseEntity<Object> handleValidationException(ValidationException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return handle(e, status);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return handle(e, status);
    }

    private static ResponseEntity<Object> handle(Exception e, HttpStatus status) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                status,
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
        );

        return new ResponseEntity<>(apiException, status);
    }
}
