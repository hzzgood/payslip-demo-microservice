package com.seismatest.payslipDemo.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.seismatest.payslipDemo.model.Payslip;
import com.seismatest.payslipDemo.model.Employee;
import com.seismatest.payslipDemo.model.TaxTable;

import lombok.NoArgsConstructor;
import lombok.Value;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;

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

    // Get Tax Table From JSON File
    private List<TaxTable> getTaxTableFromJson () {
        try {
            File taxTableFile = new ClassPathResource("/static/TaxThresholds.json").getFile();
            ObjectMapper objectMapper = new ObjectMapper();
            List<TaxTable> taxTables = objectMapper.readValue(taxTableFile, new TypeReference<List<TaxTable>>(){});
            return taxTables;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Calculate Monthly Income By Annual Salary
    private int calculateIncomeTax (int salary) {
        double result = 0.00;
        List<TaxTable> taxTables = getTaxTableFromJson();
        for (int i = 0; i < taxTables.size(); i++) {
            TaxTable currentTable = taxTables.get(i);
            if (salary >= currentTable.getTaxThresholdMin() && (salary <= currentTable.getTaxThresholdMax() || currentTable.getTaxThresholdMax() == 0)) {
                result = currentTable.getAccumulatedTax() + (salary - currentTable.getTaxThresholdMin() + 1) * currentTable.getTaxRate();
                break;
            }
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
