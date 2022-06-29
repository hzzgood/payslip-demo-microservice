package com.seismatest.payslipDemo.controller;

import com.seismatest.payslipDemo.model.Employee;
import com.seismatest.payslipDemo.service.PayslipDemoService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@Validated
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping(value = "/demo")
public class PayslipDemoController {

    @Autowired
    private PayslipDemoService payslipDemoService;

    @PostMapping(value = "/payslips", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getPayslips(@RequestBody @NotEmpty List<@Valid Employee> inputs) {
//        System.out.print(payslipDemoService.getPayslipsByEmployees(inputs));
        return ResponseEntity.ok(payslipDemoService.getPayslipsByEmployees(inputs));
    }
}

