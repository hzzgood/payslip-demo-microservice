package com.seismatest.payslipDemo;

import com.seismatest.payslipDemo.model.Employee;
import com.seismatest.payslipDemo.controller.PayslipDemoController;
import com.seismatest.payslipDemo.service.PayslipDemoService;
import com.seismatest.payslipDemo.model.Payslip;

import org.junit.jupiter.api.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

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
    public void testGetPayslipController() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Employee employee1 = new Employee("David", "Rudd", 60050, 1, 0.09);
        Employee employee2 = new Employee("Ryan", "Chen", 120000, 2, 0.1);
        List<Employee> employeeList = Arrays.asList(employee1, employee2);
        ResponseEntity<Payslip> responseEntity = payslipDemoController.getPayslips(employeeList);
        System.out.print(responseEntity);
//        assertThat(responseEntity.getStatusCodeValue() == 200);
//        System.out.print(responseEntity);
//        System.out.print(responseEntity.getBody());
    }

}
