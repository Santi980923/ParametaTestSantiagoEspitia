package com.springbootsoap.controller;

import com.springbootsoap.client.EmployeeSoapClient;
import com.springbootsoap.converter.EmployeeConverter;
import com.springbootsoap.dto.EmployeeDTO;
import com.springbootsoap.gen.AddEmployeeRequest;
import com.springbootsoap.gen.AddEmployeeResponse;
import com.springbootsoap.gen.EmployeeInfo;
import com.springbootsoap.util.EmployeeResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeSoapClient employeeSoapClient;

    @Autowired
    private EmployeeConverter employeeConverter;

    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    @GetMapping("/employee")
    public ResponseEntity<?> createEmployee(@Valid @RequestBody  EmployeeDTO employeeDTO) {
        try {
            // Convertir DTO a EmployeeInfo
            EmployeeInfo employeeInfo = employeeConverter.convertDTOToEmployeeInfo(employeeDTO);

            // Crear request
            AddEmployeeRequest request = new AddEmployeeRequest();
            request.setEmployeeInfo(employeeInfo);

            // Llamar al servicio SOAP
            AddEmployeeResponse response = employeeSoapClient.addEmployee(request);

            // Calcular tiempo de vinculación y edad
            String tiempoVinculacion = employeeConverter.calcularTiempoVinculacion(employeeDTO.getFechaVinculacion());
            String edadEmpleado = employeeConverter.calcularEdad(employeeDTO.getFechaNacimiento());

            // Crear respuesta
            return ResponseEntity.ok(new EmployeeResponse(
                    employeeConverter.convertEmployeeInfoToEmployee(employeeInfo),
                    tiempoVinculacion,
                    edadEmpleado
            ));

        } catch (Exception e) {
            log.error("Error al procesar la solicitud: ", e);
            return ResponseEntity.badRequest().body("Error al procesar la solicitud: " + e.getMessage());
        }
    }
}