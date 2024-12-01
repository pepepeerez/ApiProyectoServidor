package com.vedruna.ProyectoApi.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UrlValidator.class) // Indica que esta anotación será validada por UrlValidator
public @interface ValidUrl {
    // Mensaje que se mostrará si la URL no es válida
    String message() default "Formato de URL no válido"; 

    // Grupos de validación, se pueden usar para validar en diferentes fases
    Class<?>[] groups() default {}; 

    // Payload que se puede usar para almacenar datos adicionales
    Class<? extends Payload>[] payload() default {}; 
}

