package com.vedruna.ProyectoApi.validation;

import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UrlValidator implements ConstraintValidator<ValidUrl, String> {

    // Expresión regular para validar URLs
    private static final String URL_REGEX = "^(http|https)://[a-zA-Z0-9-_.]+(?:\\.[a-zA-Z]{2,})+(?:/[\\w-._~:/?#[\\]@!$&'()*+,;=.]+)*$";

    // Método que inicializa el validador
    @Override
    public void initialize(ValidUrl constraintAnnotation) {
        // No se necesita inicialización adicional en este caso
    }

    // Método para validar si la URL es correcta
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Si la URL es nula o vacía, se considera válida
        if (value == null || value.isEmpty()) {
            return true;
        }
        // Validación utilizando la expresión regular
        return Pattern.matches(URL_REGEX, value);
    }
}

