package com.vedruna.ProyectoApi.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vedruna.ProyectoApi.DTO.ResponseDTO;
import com.vedruna.ProyectoApi.services.ProjectServiceI;
@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectServiceI projectService;

    // Obtiene todos los proyectos con paginación
    @GetMapping("/projects")
    public Page<ProjectDTO> getAllProjects(@RequestParam("page") int page, @RequestParam("size") int size) {
        // Llama al servicio para obtener los proyectos paginados
        return projectService.showAllProjects(page, size);
    }
    
    // Obtiene un proyecto por su nombre
    @GetMapping("/projects/{name}")
    public ResponseEntity<ResponseDTO<ProjectDTO>> showProjectByName(@PathVariable String name) {
        // Busca el proyecto por su nombre
        ProjectDTO project = projectService.showProjectByName(name);
        // Si se encuentra, se devuelve el proyecto con el mensaje de éxito
        ResponseDTO<ProjectDTO> response = new ResponseDTO<>("Proyecto encontrado con éxito", project);
        return ResponseEntity.ok(response);
    }   

    // Guarda un nuevo proyecto
    @PostMapping("/projects")
    public ResponseEntity<ResponseDTO<Object>> postProject(@Valid @RequestBody Project project, BindingResult bindingResult) {
        // Verifica si hay errores de validación en los datos del proyecto
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder();
            bindingResult.getFieldErrors().forEach(error -> 
                errorMessages.append(error.getField())
                             .append(": ")
                             .append(error.getDefaultMessage())
                             .append("\n")
            );
            // Si hay errores, responde con un mensaje de error de validación
            ResponseDTO<Object> response = new ResponseDTO<>("Error de validación", errorMessages.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Verifica si la fecha de inicio es válida (debe ser antes de hoy)
        LocalDate today = LocalDate.now();
        if (!project.getStart_date().toLocalDate().isBefore(today) && !project.getEnd_date().toLocalDate().isBefore(project.getStart_date().toLocalDate())) {
            // Si la fecha no es válida, devuelve un error
            ResponseDTO<Object> response = new ResponseDTO<>("Error de validación", "La fecha de inicio debe ser antes de hoy.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Si todo es válido, guarda el proyecto
        projectService.saveProject(project);
        // Responde con un mensaje de éxito
        ResponseDTO<Object> response = new ResponseDTO<>("Proyecto creado con éxito", null);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Elimina un proyecto por su ID
    @DeleteMapping("/projects/{id}")
    public ResponseEntity<ResponseDTO<String>> deleteProject(@PathVariable Integer id) {
        // Intenta eliminar el proyecto con el ID especificado
        boolean projectDeleted = projectService.deleteProject(id);
        if (!projectDeleted) {
            // Si no se encuentra el proyecto, lanza una excepción con mensaje de error
            throw new IllegalArgumentException("No existe ningún proyecto con el ID:" + id);
        }
        // Si el proyecto se elimina correctamente, responde con un mensaje de éxito
        ResponseDTO<String> response = new ResponseDTO<>("Proyecto eliminado con éxito", "Proyecto con ID " + id + " eliminado.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Actualiza un proyecto existente
    @PutMapping("/projects/{id}")
    public ResponseEntity<ResponseDTO<Object>> updateProject(@PathVariable Integer id, @Valid @RequestBody Project project, BindingResult bindingResult) {
        // Verifica si hay errores de validación en los datos del proyecto
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder();
            bindingResult.getFieldErrors().forEach(error -> 
                errorMessages.append(error.getField())
                             .append(": ")
                             .append(error.getDefaultMessage())
                             .append("\n")
            );
            
            // Si hay errores, responde con un mensaje de error de validación
            ResponseDTO<Object> response = new ResponseDTO<>("Error de validación", errorMessages.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Intenta actualizar el proyecto
        boolean projectUpdated = projectService.updateProject(id, project);
        if (!projectUpdated) {
            // Si el proyecto no se encuentra, responde con un error
            ResponseDTO<Object> response = new ResponseDTO<>("Error", "No existe ningún proyecto con el ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        // Si el proyecto se actualiza correctamente, responde con un mensaje de éxito
        ResponseDTO<Object> response = new ResponseDTO<>("Proyecto actualizado con éxito", project);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Mueve un proyecto al estado de pruebas
    @PatchMapping("/projects/totesting/{id}")
    public ResponseEntity<String> moveProjectToTesting(@PathVariable Integer id) {
        try {
            // Intenta mover el proyecto al estado de pruebas
            boolean result = projectService.moveProjectToTesting(id);
            if (result) {
                // Si el movimiento es exitoso, responde con mensaje de éxito
                return ResponseEntity.ok("Proyecto movido a pruebas con éxito");
            } else if (projectService.findById(id) == null) {
                // Si no se encuentra el proyecto, responde con un error
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Proyecto no encontrado");
            } else {
                // Si el proyecto no se mueve, responde con un mensaje de error
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se movieron proyectos a pruebas");
            }
        } catch (Exception e) {
            // Si ocurre una excepción, responde con error interno del servidor
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al mover el proyecto a pruebas: " + e.getMessage());
        }
    }

    // Mueve un proyecto al estado de producción
    @PatchMapping("/projects/toprod/{id}")
    public ResponseEntity<String> moveProjectToProduction(@PathVariable Integer id) {
        try {
            // Intenta mover el proyecto al estado de producción
            boolean result = projectService.moveProjectToProduction(id);
            if (result) {
                // Si el movimiento es exitoso, responde con mensaje de éxito
                return ResponseEntity.ok("Proyecto movido a producción con éxito");
            } else if (projectService.findById(id) == null) {
                // Si no se encuentra el proyecto, responde con un error
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Proyecto no encontrado");
            } else {
                // Si el proyecto no se mueve, responde con un mensaje de error
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se movieron proyectos a producción");
            }
        } catch (Exception e) {
            // Si ocurre una excepción, responde con error interno del servidor
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al mover el proyecto a producción: " + e.getMessage());
        }
    }

    // Obtiene todos los proyectos que usan una tecnología específica
    @GetMapping("/projects/tec/{tech}")
    public ResponseEntity<ResponseDTO<List<ProjectDTO>>> getProjectsByTechnology(@PathVariable String tech) {
        // Llama al servicio para obtener los proyectos que utilizan la tecnología especificada
        List<ProjectDTO> projects = projectService.getProjectsByTechnology(tech);
    
        if (projects.isEmpty()) {
            // Si no se encuentran proyectos, responde con un mensaje de error
            ResponseDTO<List<ProjectDTO>> response = new ResponseDTO<>("No se encontraron proyectos con esta tecnología", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    
        // Si se encuentran proyectos, responde con los proyectos y un mensaje de éxito
        ResponseDTO<List<ProjectDTO>> response = new ResponseDTO<>("Proyectos encontrados con éxito", projects);
        return ResponseEntity.ok(response);
    }
}

