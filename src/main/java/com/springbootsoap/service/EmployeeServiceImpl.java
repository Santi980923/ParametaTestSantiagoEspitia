package com.springbootsoap.service;

import com.springbootsoap.model.Employee;
import com.springbootsoap.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeFactory;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);


    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public void AddEmployee(Employee employee) {
        employeeRepository.save(employee);
    }
}
