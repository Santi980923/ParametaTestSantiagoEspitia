package com.springbootsoap.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, List<String>> body = new HashMap<>();

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        Map<String, List<String>> body = new HashMap<>();

        if (ex.getCause() instanceof JsonMappingException) {
            JsonMappingException jsonMappingException = (JsonMappingException) ex.getCause();

            // Obtener los campos con errores de deserialización
            List<String> fieldsWithErrors = jsonMappingException.getPath().stream()
                    .map(JsonMappingException.Reference::getFieldName)
                    .distinct()
                    .toList();

            // Crear mensajes de error para cada campo
            List<String> errorMessages = fieldsWithErrors.stream()
                    .map(field -> String.format("El formato de la fecha en el campo '%s' es inválido. Se espera el formato 'yyyy-MM-dd'.", field))
                    .toList();

            body.put("errors", errorMessages);
        } else {
            // Mensaje genérico en caso de otro tipo de error de lectura de mensaje
            body.put("errors", List.of("El formato de la fecha es inválido. Se espera el formato 'yyyy-MM-dd'."));
        }

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
