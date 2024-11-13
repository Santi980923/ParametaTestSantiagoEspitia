package com.springbootsoap.util;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

public class AdultValidator implements ConstraintValidator<Adult, Date> {
    @Override
    public boolean isValid(Date dateOfBirth, ConstraintValidatorContext constraintValidatorContext) {
        if (dateOfBirth == null) {
            return false; // La fecha de nacimiento no debe ser nula
        }

        LocalDate birthDate = new java.sql.Date(dateOfBirth.getTime()).toLocalDate();
        LocalDate currentDate = LocalDate.now();
        int age = Period.between(birthDate, currentDate).getYears();

        return age >= 18;
    }
}
