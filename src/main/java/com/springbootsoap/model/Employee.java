package com.springbootsoap.model;


import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;

@Entity
@Table(name = "employee")
@Data
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "employee_id")
    private long employeeId;

    @Column(name = "Nombres")
    private String nombre;

    @Column(name = "Apellidos")
    private String apellido;

    @Column(name = "Tipo de Documento")
    private String tipoDocumento;

    @Column(name = "Numero de Documento")
    private String numeroDocumento;


    private Date fechaNacimiento;

    private Date fechaVinculacion;

    @Column(name = "cargo")
    private String cargo;

    @Column(name = "salario")
    private double salario;


}
