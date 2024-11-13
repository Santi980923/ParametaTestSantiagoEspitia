package com.springbootsoap.converter;

import com.springbootsoap.dto.EmployeeDTO;
import com.springbootsoap.gen.EmployeeInfo;
import com.springbootsoap.model.Employee;
import org.springframework.stereotype.Component;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

@Component
public class EmployeeConverter {

    public EmployeeInfo convertDTOToEmployeeInfo(EmployeeDTO dto) {
        EmployeeInfo info = new EmployeeInfo();
        info.setNombre(dto.getNombres());
        info.setApellido(dto.getApellidos());
        info.setTipoDocumento(dto.getTipoDocumento());
        info.setNumeroDocumento(dto.getNumeroDocumento());
        info.setFechaNacimiento(convertDateToXMLGregorianCalendar(dto.getFechaNacimiento()));
        info.setFechaVinculacion(convertDateToXMLGregorianCalendar(dto.getFechaVinculacion()));
        info.setCargo(dto.getCargo());
        info.setSalario(dto.getSalario());
        return info;
    }

    public Employee convertEmployeeInfoToEmployee(EmployeeInfo info) {
        Employee employee = new Employee();
        employee.setNombre(info.getNombre());
        employee.setApellido(info.getApellido());
        employee.setTipoDocumento(info.getTipoDocumento());
        employee.setNumeroDocumento(info.getNumeroDocumento());
        employee.setFechaNacimiento(convertXMLGregorianCalendarToDate(info.getFechaNacimiento()));
        employee.setFechaVinculacion(convertXMLGregorianCalendarToDate(info.getFechaVinculacion()));
        employee.setCargo(info.getCargo());
        employee.setSalario(info.getSalario());
        return employee;
    }

    public String calcularTiempoVinculacion(Date fechaVinculacion) {
        return calcularPeriodo(fechaVinculacion, new Date());
    }

    public String calcularEdad(Date fechaNacimiento) {
        return calcularPeriodo(fechaNacimiento, new Date());
    }

    private String calcularPeriodo(Date fechaInicio, Date fechaFin) {
        long diffInMillis = fechaFin.getTime() - fechaInicio.getTime();
        long days = TimeUnit.MILLISECONDS.toDays(diffInMillis);

        long years = days / 365;
        long months = (days % 365) / 30;
        long remainingDays = (days % 365) % 30;

        return String.format("%d años, %d meses, %d días", years, months, remainingDays);
    }

    private XMLGregorianCalendar convertDateToXMLGregorianCalendar(Date date) {
        try {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(date);
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
        } catch (Exception e) {
            throw new RuntimeException("Error al convertir fecha", e);
        }
    }

    private Date convertXMLGregorianCalendarToDate(XMLGregorianCalendar calendar) {
        if (calendar == null) return null;
        return calendar.toGregorianCalendar().getTime();
    }
}