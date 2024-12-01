package com.vedruna.ProyectoApi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vedruna.ProyectoApi.DTO.ResponseDTO;
import com.vedruna.ProyectoApi.services.TechnologyServiceI;
@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class TechnologyController {

    @Autowired
    private TechnologyServiceI technologyService;

    // Guarda una nueva tecnología con los proyectos asociados
    @PostMapping("/technologies")
    public ResponseEntity<String> createTechnology(@RequestBody Technology technology) {
        try {
            // Llama al servicio para guardar la tecnología
            technologyService.saveTechnology(technology);
            // Si la operación es exitosa, devuelve un mensaje con el código 201 (Creado)
            return ResponseEntity.status(HttpStatus.CREATED).body("Tecnología creada con éxito");
        } catch (IllegalArgumentException e) {
            // Si ocurre un error, como que el ID ya esté en uso, devuelve un error 400 (Solicitud incorrecta)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Elimina una tecnología por su ID
    @DeleteMapping("/technologies/{id}")
    public ResponseEntity<ResponseDTO<String>> deleteTechnology(@PathVariable Integer id) {
        // Llama al servicio para eliminar la tecnología
        boolean technologyDeleted = technologyService.deleteTechnology(id);
        if (!technologyDeleted) {
            // Si no se encuentra la tecnología, lanza una excepción con un mensaje de error
            throw new IllegalArgumentException("No existe una tecnología con el ID: " + id);
        }
        // Si la tecnología se elimina correctamente, devuelve un mensaje de éxito con el código 200 (OK)
        ResponseDTO<String> response = new ResponseDTO<>("Tecnología eliminada con éxito", null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Asocia una tecnología con un proyecto
    @PostMapping("/technologies/used/{projectId}/{technologyId}")
    public ResponseEntity<String> associateTechnologyWithProject(@PathVariable int projectId, @PathVariable int technologyId) {
        try {
            // Llama al servicio para asociar la tecnología con el proyecto
            technologyService.associateTechnologyWithProject(projectId, technologyId);
            // Si la operación es exitosa, devuelve un mensaje con el código 200 (OK)
            return ResponseEntity.status(HttpStatus.OK).body("Tecnología asociada al proyecto con éxito");
        } catch (IllegalArgumentException e) {
            // Si ocurre un error, como un ID no encontrado o inválido, devuelve un error 400
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

