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
    private Map<String, Map<String, Map<String, Double>>> groupedTotalByTimeframe;
    private Map<String, Map<String, Double>> recordNameTotalByTimeframe;
    private Map<String, Double> grandTotalByTimeframe;
}
