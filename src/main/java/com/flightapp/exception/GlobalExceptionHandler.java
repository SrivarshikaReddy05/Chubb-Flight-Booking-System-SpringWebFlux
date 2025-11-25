package com.flightapp.exception;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.server.ServerWebInputException;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Order(-1)
public class GlobalExceptionHandler {

    // 🟡 Validation errors (NotBlank, Email, etc.)
    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<?> handleValidationErrors(WebExchangeBindException ex) {

        Map<String, String> fieldErrors = new HashMap<>();
        ex.getFieldErrors().forEach(err -> fieldErrors.put(err.getField(), err.getDefaultMessage()));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(fieldErrors); 
    }

    @ExceptionHandler(ServerWebInputException.class)
    public ResponseEntity<?> handleInvalidInput(ServerWebInputException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Malformed JSON Request: " + ex.getReason());
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<?> handleDuplicateKey(DuplicateKeyException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("Duplicate Record: " + ex.getMessage());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleResponseStatus(ResponseStatusException ex) {
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(ex.getReason());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntime(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAll(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error: " + ex.getMessage());
    }
}
