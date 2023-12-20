package com.ngt.ep3.utility;

import com.ngt.ep3.model.Record;
import com.ngt.ep3.model.embeddable.RecordFields;
import com.ngt.ep3.model.embeddable.RecordValues;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

class TotalUtilityTest {

    @Test
    void testGetCountryTotal() {
        // Arrange
        List<Record> records = Arrays.asList(
                createRecord("Record1", "Country1", "2020", "Element1", "Value1", 10.0),
                createRecord("Record1", "Country1", "2020", "Element2", "Value2", 20.0),
                createRecord("Record1", "Country1", "2021", "Element1", "Value1", 15.0),
                createRecord("Record2", "Country2", "2020", "Element1", "Value1", 25.0)
                // Add more records as needed
        );

        // Act
        Map<String, Map<String, Double>> result = TotalUtility.getCountryTotal(records);

        // Assert
        assertEquals(2, result.size());
        assertEquals(2, result.get("Record1").size());
        assertEquals(1, result.get("Record2").size());

        // Add more specific assertions based on your data
    }

    @Test
    void testGetAllByGender() {
        // Arrange
        List<Record> records = Arrays.asList(
                createRecord("Record1", "Country1", "2020", "Gender", "Male", 10.0),
                createRecord("Record1", "Country1", "2020", "Gender", "Female", 20.0),
                createRecord("Record1", "Country1", "2021", "Gender", "Male", 15.0),
                createRecord("Record2", "Country2", "2020", "Gender", "Male", 25.0)
                // Add more records as needed
        );

        // Act
        Map<String, Map<String, Map<String, Double>>> result = TotalUtility.getAllByGender(records);

        // Assert
        assertEquals(2, result.size());
        assertEquals(2, result.get("Record1").size());
        assertEquals(1, result.get("Record2").size());

        // Add more specific assertions based on your data
    }

    @Test
    void testGrandTotalByYear() {
        // Arrange
        List<Record> records = Arrays.asList(
                createRecord("Record1", "Country1", "2020", "Element1", "Value1", 10.0),
                createRecord("Record1", "Country1", "2020", "Element2", "Value2", 20.0),
                createRecord("Record1", "Country1", "2021", "Element1", "Value1", 15.0),
                createRecord("Record2", "Country2", "2020", "Element1", "Value1", 25.0)
                // Add more records as needed
        );

        // Act
        Map<String, Double> result = TotalUtility.grandTotalByYear(records);

        // Assert
        assertEquals(2, result.size());
        assertNull(result.get("2020"));
        assertNull(result.get("2021"));

        // Add more specific assertions based on your data
    }

    private Record createRecord(String recordName, String country, String year, String element, String value, Double valueValue) {
        Record record = Mockito.mock(Record.class);
        when(record.getRecordName()).thenReturn(recordName);
        when(record.getFields()).thenReturn(List.of(new RecordFields(element, value)));
        when(record.getValues()).thenReturn(List.of(new RecordValues(element, valueValue)));
        return record;
    }
}
