package com.ngt.ep3.controller.advice;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RecordControllerAdviceTest {
    @Test
    void testHandleException() {
        RecordControllerAdvice recordControllerAdvice = new RecordControllerAdvice();
        ResponseEntity<Map<String, Object>> actualHandleExceptionResult = recordControllerAdvice
                .handleException(new Exception("error in deals"));
        assertEquals(3, actualHandleExceptionResult.getBody().size());
        assertTrue(actualHandleExceptionResult.hasBody());
        assertTrue(actualHandleExceptionResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualHandleExceptionResult.getStatusCode());
    }
}