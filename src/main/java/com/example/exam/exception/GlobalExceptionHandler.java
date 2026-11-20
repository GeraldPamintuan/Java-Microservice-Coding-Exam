
package com.example.exam.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(
            MethodArgumentNotValidException exception) {

        String errorMessage = exception.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("transactionStatusCode", 400);
        response.put("transactionStatusDescription", errorMessage);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(
            NotFoundException exception) {

        Map<String, Object> response = new LinkedHashMap<>();

        response.put("transactionStatusCode", 401); //401 - Unauthorized ? 404 - Not Found
        response.put("transactionStatusDescription", exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidRequest(
            HttpMessageNotReadableException exception) {

        Map<String, Object> response = new LinkedHashMap<>();

        response.put("transactionStatusCode", 400);
        response.put("transactionStatusDescription",
                "Invalid request value");

        return ResponseEntity.badRequest().body(response);
    }
}
