package com.springbootsoap.util;

import com.springbootsoap.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class EmployeeResponse {

    private Employee employee;
    private String tiempoVinculacion;
    private String edadEmpleado;
}
