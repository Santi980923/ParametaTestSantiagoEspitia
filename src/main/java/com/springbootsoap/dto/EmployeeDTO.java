package com.springbootsoap.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.springbootsoap.util.Adult;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.util.Date;


@Data
public class EmployeeDTO {

    @NotNull(message = "El nombre no debe ser nulo")
    @NotBlank(message = "El nombre no debe estar vacio")
    private String nombres;

    @NotNull(message = "El apellido no debe ser nulo")
    @NotBlank(message = "El apellido no debe estar vacio")
    private String apellidos;

    @NotNull(message = "El Tipo de documento  no debe ser nulo")
    @NotBlank(message = "El Tipo de documento no debe estar vacio")
    private String tipoDocumento;

    @NotNull(message = "El Numero de documento  no debe ser nulo")
    @NotBlank(message = "El Numero de documento no debe estar vacio")
    private String numeroDocumento;

    @NotNull(message = "La fecha de nacimiento  no debe ser nula o vacia")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    @Adult
    private Date fechaNacimiento;

    @NotNull(message = "La fecha de vinculacion  no debe ser nula o vacia")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "La fecha de vinculación debe ser en el pasado o presente")
    private Date fechaVinculacion;

    @NotNull(message = "El cargo no debe ser nulo")
    @NotBlank(message = "El cargo no debe estar vacio")
    private String cargo;

    @NotNull(message = "El salario no debe ser nulo o vacio")
    private Double salario;

}
