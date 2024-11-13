package com.springbootsoap.endpoint;

import com.springbootsoap.converter.EmployeeConverter;
import com.springbootsoap.gen.*;
import com.springbootsoap.model.Employee;
import com.springbootsoap.service.EmployeeService;
import com.springbootsoap.service.EmployeeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;

import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class EmployeeEndpoint {

    private static final String NAMESPACE_URI = "http://com.springbootsoap.allapis";

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeConverter employeeConverter;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addEmployeeRequest")
    @ResponsePayload
    public AddEmployeeResponse addEmployee(@RequestPayload AddEmployeeRequest request) {
        AddEmployeeResponse response = new AddEmployeeResponse();
        ServiceStatus serviceStatus = new ServiceStatus();

        Employee employee = new Employee();
        EmployeeInfo employeeInfo = request.getEmployeeInfo();
        employee = employeeConverter.convertEmployeeInfoToEmployee(employeeInfo);

        employeeService.AddEmployee(employee);

        // Configurar respuesta
        serviceStatus.setStatus("SUCCESS");
        serviceStatus.setMessage("Employee Added Successfully");
        response.setServiceStatus(serviceStatus);

        return response;
    }
}
