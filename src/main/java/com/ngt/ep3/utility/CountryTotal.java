package com.ngt.ep3.utility;

import com.ngt.ep3.model.Record;
import com.ngt.ep3.model.embeddable.RecordValues;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CountryTotal {
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
}
