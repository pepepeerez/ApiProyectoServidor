package com.vedruna.ProyectoApi.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vedruna.ProyectoApi.DTO.ResponseDTO;

@RestControllerAdvice
public class ExceptionController {

    // Excepciones especificas
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDTO<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        // Se crea una respuesta con el código 400 (Solicitud incorrecta) y el mensaje de la excepción
        ResponseDTO<String> response = new ResponseDTO<>("Solicitud Incorrecta", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Excepciones generales
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO<String>> handleGeneralException(Exception ex) {
        // Se crea una respuesta con el código 500 (Error interno del servidor) y un mensaje genérico de error
        ResponseDTO<String> response = new ResponseDTO<>("Error Interno del Servidor", "Ocurrió un error inesperado. Verifique que la ruta sea correcta y que los datos existan en la base de datos, y vuelva a intentarlo.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

