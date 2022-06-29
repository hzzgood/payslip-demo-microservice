package com.seismatest.payslipDemo.model;

import lombok.*;

import javax.validation.constraints.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Employee {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Min(0)
    private int annualSalary;

    @Min(1)
    @Max(12)
    private int paymentMonth;

    @DecimalMin(value = "0.00")
    @DecimalMax(value = "0.50")
    private double superRate;

    public Employee (String firstName, String lastName, int annualSalary, int superRate, int paymentMonth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.annualSalary = annualSalary;
        this.superRate = superRate;
        this.paymentMonth = paymentMonth;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public int getPaymentMonth() {
        return this.paymentMonth;
    }

    public int getAnnualSalary() {
        return this.annualSalary;
    }

    public double getSuperRate() {
        return this.superRate;
    }

}
