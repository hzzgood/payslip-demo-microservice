package com.seismatest.payslipDemo;

import com.seismatest.payslipDemo.model.Employee;
import com.seismatest.payslipDemo.controller.PayslipDemoController;
import com.seismatest.payslipDemo.service.PayslipDemoService;
import com.seismatest.payslipDemo.model.Payslip;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Arrays;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PayslipDemoControllerTest {

    @InjectMocks
    @Autowired
    private PayslipDemoController payslipDemoController;

    @Mock
    @Autowired
    private PayslipDemoService payslipDemoService;

    @Test
    public void testGetPayslipController() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Employee employee1 = new Employee("David", "Rudd", 60050, 1, 0.09);
        Employee employee2 = new Employee("Ryan", "Chen", 120000, 2, 0.1);
        List<Employee> employeeList = Arrays.asList(employee1, employee2);
        ResponseEntity<Payslip> responseEntity = payslipDemoController.getPayslips(employeeList);
        System.out.print(responseEntity);
        Payslip expectedPayslip1 = new Payslip(employee1, "01 January", "31 January", 5004, 922, 450, 4082);
        Payslip expectedPayslip2 = new Payslip(employee2, "01 February", "28 February", 10000, 2669, 1000, 7331);
        List<Payslip> expectedPayslips = Arrays.asList(expectedPayslip1, expectedPayslip2);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(expectedPayslips, responseEntity.getBody());
    }

}
