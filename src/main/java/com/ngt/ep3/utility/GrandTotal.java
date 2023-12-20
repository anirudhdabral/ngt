package com.ngt.ep3.utility;

import com.ngt.ep3.model.Record;
import com.ngt.ep3.model.embeddable.RecordValues;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class GrandTotal {
    public static Map<String, Double> grandTotalByYear(List<Record> all){

        // Calculate grand total yearwise for all records and sort by year
        Map<String, Double> grandTotalByYear = all.stream()
                .flatMap(record -> record.getValues().stream())
                .collect(Collectors.groupingBy(
                        RecordValues::getElement,
                        TreeMap::new,
                        Collectors.summingDouble(RecordValues::getValue)
                ));

//        System.out.println("grandTotalByYear: " + grandTotalByYear);
        return grandTotalByYear;
    }
}
