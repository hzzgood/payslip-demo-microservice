package com.seismatest.payslipDemo.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Period;
import javax.validation.constraints.*;
import com.seismatest.payslipDemo.model.Employee;

//import java.util.Objects;
//import java.time.LocalDate;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payslip {

    private Employee employee;
    private String fromDate;
    private String toDate;
    private Integer grossIncome;
    private Integer incomeTax;
    private Integer superannuation;
    private Integer netIncome;

//    Payslip() {}

//    public Payslip (Employee employee) {
//        this.name = employee.getFullName();
//        this.payPeriod = Period.ofMonths(employee.getPaymentMonth());
//        int incomeTax = calculateIncomeTax(employee.getAnnualSalary());
//        int grossIncome = (int)(Math.floor((double) employee.getAnnualSalary() / 12);
//        int superAmount = (int)(Math.floor((double) grossIncome * (double) employee.getSuperRate() / 100));
//        this.incomeTax = incomeTax;
//        this.grossIncome = grossIncome;
//        this.netIncome = grossIncome - incomeTax;
//        this.superAmount = superAmount;
//    }
//
//    public int calculateIncomeTax (int salary) {
//        double result = 0.00;
//        if (salary > 18200 && salary < 37001) {
//            result += (salary - 18200) * 0.19;
//        } else if (salary > 37000 && salary < 87001) {
//            result += (3572 + (salary - 37000) * 0.325);
//        } else if (salary > 87000 && salary < 180001) {
//            result += (19822 + (salary - 87000) * 0.37);
//        } else if (salary > 180000) {
//            result += (54232 + (salary - 180000) * 0.45);
//        }
//        return (int) Math.ceil(result / 12);
//    }


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
