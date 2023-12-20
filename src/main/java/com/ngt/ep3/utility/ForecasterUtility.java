package com.ngt.ep3.utility;

import com.ngt.ep3.model.Record;
import com.ngt.ep3.model.embeddable.RecordFields;
import com.ngt.ep3.model.embeddable.RecordValues;

import java.util.*;
import java.util.stream.Collectors;

import static com.ngt.ep3.constants.NgtEp3Constants.UNKNOWN_ELEMENT;

public class ForecasterUtility {
    public static Map<String, Map<String, Map<String, Double>>> getGroupedTotalByTimeframe(List<Record> all) {

        // Sum values by year and fields_element for each recordName
        Map<String, Map<String, Map<String, Double>>> sumByTimeframeAndFieldElement = all.stream()
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

    public static Map<String, Map<String, Double>> getRecordNameTotalByTimeframe(List<Record> data) {

        // Sum values by year for each recordName
        Map<String, Map<String, Double>> sumByRecordName = data.stream()
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

    public static Map<String, Double> getGrandTotalByTimeframe(List<Record> all) {

        // Calculate grand total yearwise for all records and sort by year
        return all.stream()
                .flatMap(record -> record.getValues().stream())
                .collect(Collectors.groupingBy(
                        RecordValues::getElement,
                        TreeMap::new,
                        Collectors.summingDouble(RecordValues::getValue)
                ));
    }
}
