package com.ngt.ep3.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeframeTotal {
    private String timeframeName;

    private double timeframeTotal;
}
