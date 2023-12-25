package com.ngt.ep3.controller;

import com.ngt.ep3.model.Record;
import com.ngt.ep3.model.response_DTO.BackendResponse;
import com.ngt.ep3.service.impl.RecordServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

class RecordControllerTest {

    @Mock
    private RecordServiceImpl service;

    @InjectMocks
    private RecordController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllRecords() {
        // Arrange
        List<Record> records = new ArrayList<>();
        when(service.getAllRecords()).thenReturn(records);

        // Act
        ResponseEntity<List<Record>> responseEntity = controller.getAllRecords();

        // Assert
        assertEquals(records, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetRecordById() {
        // Arrange
        int recordId = 1;
        Record record = new Record();
        when(service.getRecord(recordId)).thenReturn(record);

        // Act
        ResponseEntity<Record> responseEntity = controller.getRecordById(recordId);

        // Assert
        assertEquals(record, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testAddRecord() {
        // Arrange
        Record recordToAdd = new Record();
        when(service.addRecord(recordToAdd)).thenReturn(recordToAdd);

        // Act
        ResponseEntity<Record> responseEntity = controller.addRecord(recordToAdd);

        // Assert
        assertEquals(recordToAdd, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testUpdateRecord() {
        // Arrange
        Record updatedRecord = new Record();
        when(service.updateRecord(updatedRecord)).thenReturn(updatedRecord);

        // Act
        ResponseEntity<Record> responseEntity = controller.updateRecord(updatedRecord);

        // Assert
        assertEquals(updatedRecord, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testDeleteRecord() {
        // Arrange
        int recordId = 1;
        when(service.deleteRecord(recordId)).thenReturn(true);

        // Act
        ResponseEntity<Boolean> responseEntity = controller.deleteRecord(recordId);

        // Assert
        assertEquals(Boolean.TRUE, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testDeleteRecordNotFound() {
        // Arrange
        int recordId = 1;
        when(service.deleteRecord(recordId)).thenReturn(false);

        // Act
        ResponseEntity<Boolean> responseEntity = controller.deleteRecord(recordId);

        // Assert
        assertNotEquals(Boolean.TRUE, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

//    @Test
//    void testGetForcastedResults() {
//        // Arrange
//        BackendResponse backendResponse = new BackendResponse();
//        when(service.getForecastedResults()).thenReturn(backendResponse);
//
//        // Act
//        ResponseEntity<BackendResponse> responseEntity = controller.getForcastedResults();
//
//        // Assert
//        assertEquals(backendResponse, responseEntity.getBody());
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//    }

//    @Test
//    void testPostNextYearForcastedResult() {
//        // Arrange
//        Record record = new Record();
//        when(service.nextYearForcastedResult()).thenReturn("Next year forecast result");
//
//        // Act
//        ResponseEntity<String> responseEntity = controller.postNextYearForcastedResult();
//
//        // Assert
//        assertEquals("Next year forecast result", responseEntity.getBody());
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//    }
}
