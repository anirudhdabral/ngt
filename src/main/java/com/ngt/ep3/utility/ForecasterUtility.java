package com.ngt.ep3.utility;

import com.ngt.ep3.model.Record;
import com.ngt.ep3.model.embeddable.RecordFields;
import com.ngt.ep3.model.embeddable.RecordValues;

import java.util.*;
import java.util.stream.Collectors;

import static com.ngt.ep3.constants.NgtEp3Constants.UNKNOWN_ELEMENT;

public class ForecasterUtility {
    public static Map<String, Map<String, Map<String, Double>>> getGroupedTotalByTimeframe(List<Record> records) {

        // Sum values by year and fields_element for each recordName
        Map<String, Map<String, Map<String, Double>>> sumByTimeframeAndFieldElement = records.stream()
                .collect(Collectors.groupingBy(
                        Record::getRecordName,
                        Collectors.groupingBy(
                                record -> record.getFields().stream()
                                        .filter(field -> record.getFields().get(0).getElement().equals(field.getElement()))
                                        .findFirst()
                                        .map(RecordFields::getValue)
                                        .orElse(UNKNOWN_ELEMENT),
                                Collectors.flatMapping(
                                        record -> record.getValues().stream(),
                                        Collectors.groupingBy(
                                                RecordValues::getElement,
                                                Collectors.summingDouble(RecordValues::getValue)
                                        )
                                )
                        )
                ));

        // Sort the innermost map by year in ascending order
        sumByTimeframeAndFieldElement.forEach((recordName, genderMap) ->
                genderMap.forEach((gender, yearMap) ->
                        genderMap.put(gender, yearMap.entrySet().stream()
                                .sorted(Map.Entry.comparingByKey())
                                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new)))));

        return sumByTimeframeAndFieldElement;
    }

    public static Map<String, Map<String, Double>> getRecordNameTotalByTimeframe(List<Record> records) {

        // Sum values by year for each recordName
        Map<String, Map<String, Double>> sumByRecordName = records.stream()
                .collect(Collectors.groupingBy(Record::getRecordName,
                        Collectors.flatMapping(record -> record.getValues().stream(),
                                Collectors.groupingBy(RecordValues::getElement, Collectors.summingDouble(RecordValues::getValue)))));

        // Sort the inner map by year in ascending order
        sumByRecordName.forEach((recordName, innerMap) ->
                sumByRecordName.put(recordName, innerMap.entrySet().stream()
                        .sorted(Comparator.comparing(Map.Entry::getKey))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new))));

        return sumByRecordName;
    }

    public static Map<String, Double> getGrandTotalByTimeframe(List<Record> records) {

        // Calculate grand total yearwise for all records and sort by year
        return records.stream()
                .flatMap(record -> record.getValues().stream())
                .collect(Collectors.groupingBy(
                        RecordValues::getElement,
                        TreeMap::new,
                        Collectors.summingDouble(RecordValues::getValue)
                ));
    }

    public static void distributeValues(List<Record> records, String recordName, String gender, int targetYear, double totalMaleInTargetYear) {
        // Calculate the ratio for each age group based on the preceding year
        Map<String, Double> ratioMap = calculateRatio(records, recordName, gender, targetYear);

        // Distribute the totalMaleInTargetYear proportionally based on the calculated ratio
        Map<String, Double> distributedValues = ratioMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue() * totalMaleInTargetYear
                ));

        // Display the distributed values
        System.out.println("Distributed Values for " + recordName + ", Gender: " + gender + ", Year: " + targetYear);
        distributedValues.forEach((age, value) ->
                System.out.println("Age: " + age + ", Value: " + value)
        );
    }

    private static Map<String, Double> calculateRatio(List<Record> records, String recordName, String gender, int targetYear) {
        // Filter records for the specified recordName, gender, and targetYear
        List<Record> relevantRecords = records.stream()
                .filter(record ->
                        record.getRecordName().equals(recordName) &&
                                record.getFields().stream().anyMatch(field ->
                                        "Gender".equals(field.getElement()) && gender.equals(field.getValue())
                                )
                )
                .toList();

        // Calculate the total Male value in the preceding year (e.g., Year 2020)
        int precedingYear = targetYear - 1;
        double totalMaleInPrecedingYear = relevantRecords.stream()
                .filter(record -> "male".equals(getGender(record)))
                .flatMap(record -> record.getValues().stream())
                .filter(value -> value.getElement().equals(Integer.toString(precedingYear)))
                .mapToDouble(RecordValues::getValue)
                .sum();


        // Calculate the ratio for each age group based on the preceding year
        return relevantRecords.stream()
                .flatMap(record -> record.getValues().stream())
                .filter(value -> value.getElement().equals(Integer.toString(precedingYear)))
                .collect(Collectors.toMap(
                        RecordValues::getElement,
                        value -> totalMaleInPrecedingYear > 0 ? value.getValue() / totalMaleInPrecedingYear : 0
                ));
    }

    private static String getGender(Record record) {
        return record.getFields().stream()
                .filter(field -> "Gender".equals(field.getElement()))
                .findFirst()
                .map(RecordFields::getValue)
                .orElse("Unknown");
    }

}
