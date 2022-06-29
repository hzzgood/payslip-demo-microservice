package com.seismatest.payslipDemo;

import com.seismatest.payslipDemo.model.Employee;
import com.seismatest.payslipDemo.service.PayslipDemoService;
import com.seismatest.payslipDemo.model.Payslip;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import java.util.List;
import java.util.Arrays;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PayslipDemoServiceTest {
    @Autowired
    private PayslipDemoService payslipDemoService;

    @Test
    public void testGetPayslipService() throws Exception {
        Employee employee1 = new Employee("David", "Rudd", 60050, 1, 0.09);
        Employee employee2 = new Employee("Ryan", "Chen", 120000, 2, 0.1);
        Employee employee3 = new Employee("Amy", "Tan", 1100000, 3, 0.1);
        List<Employee> employeeList = Arrays.asList(employee1, employee2, employee3);
        List<Payslip> payslips = payslipDemoService.getPayslipsByEmployees(employeeList);
        System.out.print(payslips);
        Payslip expectedPayslip1 = new Payslip(employee1, "01 January", "31 January", 5004, 922, 450, 4082);
        Payslip expectedPayslip2 = new Payslip(employee2, "01 February", "28 February", 10000, 2669, 1000, 7331);
        Payslip expectedPayslip3 = new Payslip(employee3, "01 March", "31 March", 91667, 39019, 9167, 52648);
        assertEquals(expectedPayslip1, payslips.get(0), "compare payslip 1");
        assertEquals(expectedPayslip2, payslips.get(1), "compare payslip 2");
        assertEquals(expectedPayslip3, payslips.get(2), "compare payslip 3");
    }

}
