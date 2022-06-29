package com.seismatest.payslipDemo.model;

import lombok.*;

import com.seismatest.payslipDemo.model.Employee;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Payslip {

    private Employee employee;
    private String fromDate;
    private String toDate;
    private int grossIncome;
    private int incomeTax;
    private int superannuation;
    private int netIncome;

}
