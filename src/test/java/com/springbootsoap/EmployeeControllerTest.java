package com.springbootsoap;

import com.springbootsoap.client.EmployeeSoapClient;
import com.springbootsoap.controller.EmployeeController;
import com.springbootsoap.converter.EmployeeConverter;
import com.springbootsoap.dto.EmployeeDTO;
import com.springbootsoap.gen.AddEmployeeRequest;
import com.springbootsoap.gen.AddEmployeeResponse;
import com.springbootsoap.gen.EmployeeInfo;
import com.springbootsoap.model.Employee;
import com.springbootsoap.util.EmployeeResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Use Mockito Extension for JUnit 5
public class EmployeeControllerTest {

    @Mock
    private EmployeeSoapClient employeeSoapClient;

    @Mock
    private EmployeeConverter employeeConverter;

    @InjectMocks
    private EmployeeController employeeController;

    private EmployeeDTO validEmployeeDTO;
    private EmployeeDTO invalidEmployeeDTO;

    @BeforeEach
    void setUp() {
        // Create a valid EmployeeDTO
        validEmployeeDTO = new EmployeeDTO();
        validEmployeeDTO.setNombres("John");
        validEmployeeDTO.setApellidos("Doe");
        validEmployeeDTO.setTipoDocumento("DNI");
        validEmployeeDTO.setNumeroDocumento("12345678");
        validEmployeeDTO.setFechaNacimiento(parseDate("2000-01-01"));
        validEmployeeDTO.setFechaVinculacion(parseDate("2020-01-01"));
        validEmployeeDTO.setCargo("Developer");
        validEmployeeDTO.setSalario(1000.0);

        // Create an invalid EmployeeDTO (minor employee)
        invalidEmployeeDTO = new EmployeeDTO();
        invalidEmployeeDTO.setNombres("Jane");
        invalidEmployeeDTO.setApellidos("Doe");
        invalidEmployeeDTO.setTipoDocumento("DNI");
        invalidEmployeeDTO.setNumeroDocumento("87654321");
        invalidEmployeeDTO.setFechaNacimiento(parseDate("2010-01-01"));
        invalidEmployeeDTO.setFechaVinculacion(parseDate("2023-01-01"));
        invalidEmployeeDTO.setCargo("Intern");
        invalidEmployeeDTO.setSalario(500.0);
    }

    private Date parseDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return java.sql.Date.valueOf(localDate);
    }

    @Test
    void testCreateEmployee_valid() throws Exception {
        // Mock the converter methods
        EmployeeInfo employeeInfo = mock(EmployeeInfo.class);
        when(employeeConverter.convertDTOToEmployeeInfo(validEmployeeDTO)).thenReturn(employeeInfo);

        // Mock the conversion to Employee
        Employee employee = mock(Employee.class);
        when(employeeConverter.convertEmployeeInfoToEmployee(employeeInfo)).thenReturn(employee);

        // Mock the SOAP client response
        AddEmployeeResponse soapResponse = mock(AddEmployeeResponse.class);
        when(employeeSoapClient.addEmployee(any(AddEmployeeRequest.class))).thenReturn(soapResponse);

        // Mock the calculation methods
        when(employeeConverter.calcularTiempoVinculacion(validEmployeeDTO.getFechaVinculacion())).thenReturn("3 years");
        when(employeeConverter.calcularEdad(validEmployeeDTO.getFechaNacimiento())).thenReturn("24 years");

        // Call the controller method
        ResponseEntity<?> response = employeeController.createEmployee(validEmployeeDTO);

        // Check the response status
        assertEquals(200, response.getStatusCodeValue());

        // Check if the response body is an instance of EmployeeResponse
        assertTrue(response.getBody() instanceof EmployeeResponse);

        // Check if the body contains expected values (you can add further checks if needed)
        EmployeeResponse employeeResponse = (EmployeeResponse) response.getBody();
        assertNotNull(employeeResponse.getEmployee());
        assertEquals("3 years", employeeResponse.getTiempoVinculacion());
        assertEquals("24 years", employeeResponse.getEdadEmpleado());
    }

    
    @Test
    void testCreateEmployee_soapServiceError() throws Exception {
        // Mock del convertidor
        EmployeeInfo employeeInfo = mock(EmployeeInfo.class);
        when(employeeConverter.convertDTOToEmployeeInfo(validEmployeeDTO)).thenReturn(employeeInfo);

        // Simular un fallo en la llamada al servicio SOAP
        when(employeeSoapClient.addEmployee(any(AddEmployeeRequest.class))).thenThrow(new RuntimeException("SOAP Service error"));

        // Llamar al controlador y capturar la respuesta
        ResponseEntity<?> response = employeeController.createEmployee(validEmployeeDTO);

        // Verificar que el código de estado sea 400 y que se devuelva el mensaje de error esperado
        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof String);
        assertEquals("Error al procesar la solicitud: SOAP Service error", response.getBody());
    }


    @Test
    void testCreateEmployee_internalError() throws Exception {
        // Simular una excepción general en el controlador
        doThrow(new RuntimeException("Unexpected error")).when(employeeConverter).convertDTOToEmployeeInfo(validEmployeeDTO);

        // Llamar al controlador y capturar la respuesta
        ResponseEntity<?> response = employeeController.createEmployee(validEmployeeDTO);

        // Verificar que el código de estado sea 400 y que el mensaje sea el de un error inesperado
        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof String);
        assertEquals("Error al procesar la solicitud: Unexpected error", response.getBody());
    }


}
