package com.ngt.ep3.service.impl;

import com.ngt.ep3.model.Record;
import com.ngt.ep3.model.response_DTO.BackendResponse;
import com.ngt.ep3.repository.RecordRepository;
import com.ngt.ep3.utility.ForecasterUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecordServiceImplTest {

    @Mock
    private RecordRepository repository;

    @InjectMocks
    private RecordServiceImpl recordService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllRecords() {
        // Arrange
        List<Record> records = Collections.singletonList(new Record());
        when(repository.findAll()).thenReturn(records);

        // Act
        List<Record> result = recordService.getAllRecords();

        // Assert
        assertEquals(records, result);
    }

    @Test
    void testGetRecordById() {
        // Arrange
        int recordId = 1;
        Record expectedRecord = new Record();
        when(repository.findById(recordId)).thenReturn(Optional.of(expectedRecord));

        // Act
        Record result = recordService.getRecord(recordId);

        // Assert
        assertEquals(expectedRecord, result);
    }

    @Test
    void testGetRecordNotFound() {
        // Arrange
        int recordId = 1;
        when(repository.findById(recordId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(RuntimeException.class, () -> recordService.getRecord(recordId));
    }

    @Test
    void testAddRecord() {
        // Arrange
        Record recordToAdd = new Record();
        when(repository.save(recordToAdd)).thenReturn(recordToAdd);

        // Act
        Record result = recordService.addRecord(recordToAdd);

        // Assert
        assertEquals(recordToAdd, result);
        verify(repository, times(1)).save(recordToAdd);
    }

    @Test
    void testUpdateRecord() {
        // Arrange
        Record existingRecord = new Record();
        Record updatedRecord = new Record();
        when(repository.findById(updatedRecord.getId())).thenReturn(Optional.of(existingRecord));
        when(repository.save(existingRecord)).thenReturn(existingRecord);

        // Act
        Record result = recordService.updateRecord(updatedRecord);

        // Assert
        assertEquals(existingRecord, result);
        verify(repository, times(1)).findById(updatedRecord.getId());
        verify(repository, times(1)).save(existingRecord);
    }

    @Test
    void testUpdateRecordNotFound() {
        // Arrange
        Record updatedRecord = new Record();
        when(repository.findById(updatedRecord.getId())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(RuntimeException.class, () -> recordService.updateRecord(updatedRecord));
        verify(repository, times(1)).findById(updatedRecord.getId());
        verify(repository, never()).save(any());
    }

    @Test
    void testGetForecastedResults() {
        // Arrange
        List<Record> allRecords = new ArrayList<>();

        // Mocking repository behavior
        when(repository.findAll()).thenReturn(allRecords);
        Map<String, Map<String, Map<String, Double>>> sumByYearAndGender = new HashMap<>();
        Map<String, Map<String, Double>> countryTotal = new HashMap<>();
        Map<String, Double> grandTotalByYear = new HashMap<>();

        try (MockedStatic<ForecasterUtility> totalUtilityMock = mockStatic(ForecasterUtility.class)) {
            totalUtilityMock.when(() -> ForecasterUtility.getGroupedTotalByTimeframe(allRecords)).thenReturn(sumByYearAndGender);
            totalUtilityMock.when(() -> ForecasterUtility.getRecordNameTotalByTimeframe(allRecords)).thenReturn(countryTotal);
            totalUtilityMock.when(() -> ForecasterUtility.getGrandTotalByTimeframe(allRecords)).thenReturn(grandTotalByYear);

            // Act
            BackendResponse result = recordService.getForecastedResults();

            // Assert
            assertEquals(allRecords, result.getRecordList());
            assertEquals(sumByYearAndGender, result.getGroupedTotalByTimeframe());
            assertEquals(countryTotal, result.getRecordNameTotalByTimeframe());
            assertEquals(grandTotalByYear, result.getGrandTotalByTimeframe());

            // Verify that repository and ForecasterUtility methods were called
            verify(repository, times(1)).findAll();
            totalUtilityMock.verify(() -> ForecasterUtility.getGroupedTotalByTimeframe(allRecords));
            totalUtilityMock.verify(() -> ForecasterUtility.getRecordNameTotalByTimeframe(allRecords));
            totalUtilityMock.verify(() -> ForecasterUtility.getGrandTotalByTimeframe(allRecords));
        }
    }

    @Test
    void testDeleteRecord() {
        // Arrange
        int recordId = 1;
        doNothing().when(repository).deleteById(recordId);

        // Act
        boolean result = recordService.deleteRecord(recordId);

        // Assert
        assertTrue(result);
        verify(repository, times(1)).deleteById(recordId);
    }

    @Test
    void testDeleteRecordNotFound() {
        // Arrange
        int recordId = 1;
        doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(recordId);

        // Act
        boolean result = recordService.deleteRecord(recordId);

        // Assert
        assertFalse(result);
        verify(repository, times(1)).deleteById(recordId);
    }

    //    @Test
//    void testPostNextYearForcastedResult() {
//        // Arrange
//        List<Record> allRecords = Collections.singletonList(new Record());
//        when(repository.findAll()).thenReturn(allRecords);
//
//        // Act
//        String result = recordService.nextYearForcastedResult();
//
//        // Assert
//        assertNotNull(result);
//        assertTrue(result.contains("postNextYearForcastedResult API is under construction"));
//        assertTrue(result.contains(allRecords.toString()));
//    }
}
