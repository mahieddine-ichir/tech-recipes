package com.thinatech.recipes;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Map<Object, Object> onConstraintViolation(ConstraintViolationException exception) {
        return ofError(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    Map<Object, Object> ofError(HttpStatus httpStatus, String message) {
        return Map.of("error", httpStatus, "details", message);
    }
}
