package com.seismatest.payslipDemo.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class TaxTable {

    private int taxThresholdMin;
    private int taxThresholdMax;
    private double taxRate;
    private int accumulatedTax;

}
