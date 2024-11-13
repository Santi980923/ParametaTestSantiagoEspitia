package com.springbootsoap.util;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = AdultValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Adult {

    String message() default "El empleado debe ser mayor de edad (18 años o más)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
