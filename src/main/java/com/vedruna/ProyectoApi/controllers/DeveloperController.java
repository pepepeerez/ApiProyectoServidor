package com.vedruna.ProyectoApi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vedruna.ProyectoApi.DTO.ResponseDTO;
import com.vedruna.ProyectoApi.persistance.models.Developer;
import com.vedruna.ProyectoApi.persistance.models.Project;
import com.vedruna.ProyectoApi.services.DeveloperServiceI;
import com.vedruna.ProyectoApi.services.ProjectServiceI;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class DeveloperController {

    @Autowired
    private DeveloperServiceI developerService;

    @Autowired
    private ProjectServiceI projectService;

    // Método para crear un nuevo desarrollador
    @PostMapping("/developers")
    public ResponseEntity<ResponseDTO<String>> postDeveloper(@RequestBody Developer developer) {
        developerService.saveDeveloper(developer); // Se guarda el desarrollador en la base de datos
        ResponseDTO<String> response = new ResponseDTO<>("Desarrollador creado exitosamente", null);
        return ResponseEntity
                .status(HttpStatus.CREATED) // Retorna el código de estado HTTP 201 (Creado)
                .body(response);
    }

    // Método para eliminar un desarrollador dado su ID
    @DeleteMapping("/developers/{id}")
    public ResponseEntity<ResponseDTO<String>> deleteDeveloper(@PathVariable Integer id) {
        boolean developerDeleted = developerService.deleteDeveloper(id); // Se intenta eliminar el desarrollador
        if (!developerDeleted) {
            throw new IllegalArgumentException("No existe un desarrollador con el ID: " + id); // Si no se encuentra el desarrollador, se lanza una excepción
        }
        ResponseDTO<String> response = new ResponseDTO<>("Desarrollador eliminado exitosamente", null);
        return ResponseEntity.status(HttpStatus.OK).body(response); // Retorna un código de estado HTTP 200 (OK)
    }

    // Método para asignar un desarrollador a un proyecto
    @PostMapping("/developers/worked/{developerId}/{projectId}")
    public ResponseEntity<?> addDeveloperToProject(@PathVariable int developerId, @PathVariable int projectId) {
        Developer developer = developerService.findById(developerId); // Se busca el desarrollador por su ID
        Project project = projectService.findById(projectId); // Se busca el proyecto por su ID

        if (developer == null) {
            return ResponseEntity.badRequest().body("Desarrollador no encontrado"); // Si el desarrollador no existe, se retorna un error
        }

        if (project == null) {
            return ResponseEntity.badRequest().body("Proyecto no encontrado"); // Si el proyecto no existe, se retorna un error
        }

        // Verifica si el desarrollador ya está asignado al proyecto
        if (!project.getDevelopers().contains(developer)) {
            project.getDevelopers().add(developer); // Se agrega el desarrollador al proyecto
            developer.getProjectsDevelopers().add(project); // Se agrega el proyecto al desarrollador
            projectService.saveProject(project); // Se guardan los cambios en el proyecto
            developerService.saveDeveloper(developer); // Se guardan los cambios en el desarrollador
        }

        return ResponseEntity.ok("Desarrollador añadido al proyecto"); // Si todo es exitoso, se devuelve un mensaje de éxito
    }
}


