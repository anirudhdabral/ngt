package com.ngt.ep3.utility;

import com.ngt.ep3.model.Record;
import com.ngt.ep3.model.embeddable.RecordFields;
import com.ngt.ep3.model.embeddable.RecordValues;

import java.util.*;
import java.util.stream.Collectors;

public class TotalUtility {
    public static Map<String, Map<String, Double>> getCountryTotal(List<Record> data){

        // Sum values by year for each recordName
        Map<String, Map<String, Double>> getCountryTotal = data.stream()
                .collect(Collectors.groupingBy(Record::getRecordName,
                        Collectors.flatMapping(record -> record.getValues().stream(),
                                Collectors.groupingBy(RecordValues::getElement, Collectors.summingDouble(RecordValues::getValue)))));

        // Sort the inner map by year in ascending order
        getCountryTotal.forEach((recordName, innerMap) ->
                getCountryTotal.put(recordName, innerMap.entrySet().stream()
                        .sorted(Comparator.comparing(Map.Entry::getKey))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new))));

        return getCountryTotal;
    }

    public static Map<String, Map<String, Map<String, Double>>> getAllByGender(List<Record> all){

        // Sum values by year and gender for each recordName
        Map<String, Map<String, Map<String, Double>>> sumByYearAndGender = all.stream()
                .collect(Collectors.groupingBy(
                        Record::getRecordName,
                        Collectors.groupingBy(
                                record -> record.getFields().stream()
                                        .filter(field -> "Gender".equals(field.getElement()))
                                        .findFirst()
                                        .map(RecordFields::getValue)
                                        .orElse("Unknown"),
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
        sumByYearAndGender.forEach((recordName, genderMap) ->
                genderMap.forEach((gender, yearMap) ->
                        genderMap.put(gender, yearMap.entrySet().stream()
                                .sorted(Map.Entry.comparingByKey())
                                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new)))));

        return sumByYearAndGender;
    }

    public static Map<String, Double> grandTotalByYear(List<Record> all){

        // Calculate grand total yearwise for all records and sort by year
        Map<String, Double> grandTotalByYear = all.stream()
                .flatMap(record -> record.getValues().stream())
                .collect(Collectors.groupingBy(
                        RecordValues::getElement,
                        TreeMap::new,
                        Collectors.summingDouble(RecordValues::getValue)
                ));

        return grandTotalByYear;
    }
}
