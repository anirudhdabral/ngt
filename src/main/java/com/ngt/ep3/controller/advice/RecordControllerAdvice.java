package com.ngt.ep3.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static com.ngt.ep3.constants.NgtEp3Constants.*;

@RestControllerAdvice
public class RecordControllerAdvice {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Map<String, Object>> handleException(Exception e) {
        return buildErrorResponse(e.getMessage());
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(String message) {
        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put(ERROR_MESSAGE, message);
        errorMap.put(ERROR_CODE, HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorMap.put(ERROR_TIMESTAMP, formatTimestamp(LocalDateTime.now()));

        return new ResponseEntity<>(errorMap, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String formatTimestamp(LocalDateTime timestamp) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER);
        return timestamp.format(formatter);
    }
}
