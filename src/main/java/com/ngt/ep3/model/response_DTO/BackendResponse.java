package com.ngt.ep3.model.response_DTO;

import com.ngt.ep3.model.Record;
import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class BackendResponse {
    private List<Record> recordList;
    private Map<String, Map<String, Map<String, Double>>> genderTotalByYear;
    private Map<String, Map<String, Double>> countryTotalByYear;
    private Map<String, Double> grandTotalByYear;
}
