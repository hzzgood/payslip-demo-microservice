package com.seismatest.payslipDemo.service;

import com.seismatest.payslipDemo.model.Payslip;
import com.seismatest.payslipDemo.model.Employee;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.text.DateFormatter;
import java.text.DateFormat;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.time.Period;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.util.Calendar;

@NoArgsConstructor
@Service
public class PayslipDemoService {

    // Calculate Payslip Stats By List of Employees
    public List<Payslip> getPayslipsByEmployees (List<Employee> employees) {
        List<Payslip> payslips = employees.stream()
                .map(employee -> getPayslipByEmployee(employee))
                .collect(Collectors.toList());
        return payslips;
    }

    // Calculate Payslip Stats for Each Employee
    private Payslip getPayslipByEmployee (Employee employee) {
        Payslip payslip = new Payslip();
        payslip.setEmployee(employee);
        payslip.setFromDate(getFromDate(employee.getPaymentMonth()));
        payslip.setToDate(getToDate(employee.getPaymentMonth()));
        int incomeTax = calculateIncomeTax(employee.getAnnualSalary());
        int grossIncome = (int) Math.round((double)employee.getAnnualSalary() / 12);
        int superAmount = (int) Math.round((double) grossIncome *  employee.getSuperRate());
        payslip.setIncomeTax(incomeTax);
        payslip.setGrossIncome(grossIncome);
        payslip.setNetIncome(grossIncome - incomeTax);
        payslip.setSuperannuation(superAmount);
        return payslip;
    }

    // Calculate Monthly Income By Annual Salary
    private int calculateIncomeTax (int salary) {
        double result = 0.00;
        if (salary > 18200 && salary < 37001) {
            result = (salary - 18200) * 0.19;
        } else if (salary > 37000 && salary < 87001) {
            result = (3572 + (salary - 37000) * 0.325);
        } else if (salary > 87000 && salary < 180001) {
            result = (19822 + (salary - 87000) * 0.37);
        } else if (salary > 180000) {
            result = (54232 + (salary - 180000) * 0.45);
        }
        return (int) Math.round(result / 12);
    }

    // Get From Date By Month
    private String getFromDate (int month) {
        int year = Year.now().getValue();
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate monthBegin = yearMonth.atDay(1);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM");
        return monthBegin.format(dateTimeFormatter);
    }

    // Get To Date By Month
    private String getToDate (int month) {
        int year = Year.now().getValue();
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate monthEnd = yearMonth.atEndOfMonth();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM");
        return monthEnd.format(dateTimeFormatter);
    }

}
