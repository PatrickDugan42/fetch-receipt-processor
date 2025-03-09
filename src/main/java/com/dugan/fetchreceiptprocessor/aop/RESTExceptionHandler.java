package com.dugan.fetchreceiptprocessor.aop;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestControllerAdvice
public class RESTExceptionHandler {

    // Handles validation exception, adds error message defined in dto validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("message", "The receipt is invalid.");

        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        response.put("errors", errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PointsNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlePointsNotFound(PointsNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "status", 404,
                        "message", "No receipt found for that ID."
                ));
    }

    //Handle status and message when the /receipts/{id}/points passed id is not a UUID
    // Without this handling an unwanted 500 is thrown when a non UUID is used
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidUUIDTypeForPathParameter(MethodArgumentTypeMismatchException exception,
                                                                                 HttpServletRequest request) {

        // capture path parameter using [^/]+ and validate it is UUID or not
        if(request.getRequestURI().matches("^/receipts/[^/]+/points$")){
            String[] split = request.getRequestURI().split("/");
            if(!isValidUUID(split[2])){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of(
                                "status", 404,
                                "message", "No receipt found for that ID."
                        ));
            }
        }

        //if not a UUID format error throw possible unhandled exception
        throw exception;
    }

    private boolean isValidUUID(String value) {
        try {
            UUID.fromString(value); //try to cast to throw exception if not valid
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}