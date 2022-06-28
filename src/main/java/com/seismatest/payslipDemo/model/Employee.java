package com.seismatest.payslipDemo.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.*;

//import java.util.Objects;
//import java.time.LocalDate;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

//    public String getFullName() {
//        return this.firstName + " " + this.lastName;
//    }

    public int getPaymentMonth() {
        return this.paymentMonth;
    }

    public int getAnnualSalary() {
        return this.annualSalary;
    }

    public double getSuperRate() {
        return this.superRate;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o)
//            return true;
//        if (!(o instanceof Employee))
//            return false;
//        Employee employee = (Employee) o;
//        return Objects.equals(this.id, employee.id) && Objects.equals(this.name, employee.name)
//                && Objects.equals(this.role, employee.role);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(this.id, this.name, this.role);
//    }
//
//    @Override
//    public String toString() {
//        return "Employee{" + "id=" + this.id + ", name='" + this.name + '\'' + ", role='" + this.role + '\'' + '}';
//    }
}
