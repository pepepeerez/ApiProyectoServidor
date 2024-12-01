package com.vedruna.ProyectoApi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vedruna.ProyectoApi.persistance.models.Developer;
import com.vedruna.ProyectoApi.persistance.models.Project;
import com.vedruna.ProyectoApi.persistance.repositories.DeveloperRepository;
import com.vedruna.ProyectoApi.persistance.repositories.ProjectRepositoryI;

@Service
public class DeveloperServiceImpl implements DeveloperServiceI {

    @Autowired
    DeveloperRepository developerRepository;

    @Autowired
    ProjectRepositoryI projectRepository;

    @Override
    public void saveDeveloper(Developer developer) {
        // Lista para almacenar los proyectos asociados que existen en la base de datos
        List<Project> managedProjects = new ArrayList<>();
        
        // Itera sobre los proyectos asociados al desarrollador para verificar si existen
        for (Project project : developer.getProjectsDevelopers()) {
            projectRepository.findById(project.getId()).ifPresentOrElse(
                managedProjects::add,  // Si existe, lo añade a la lista
                () -> { 
                    // Si no existe, lanza una excepción con un mensaje
                    throw new IllegalArgumentException("No existe ningún proyecto con el ID: " + project.getId());
                }
            );
        }

        // Asocia los proyectos encontrados con el desarrollador
        developer.setProjectsDevelopers(managedProjects);

        // Guarda el desarrollador con los proyectos asociados
        developerRepository.save(developer);
    }

    @Override
    public boolean deleteDeveloper(Integer id) {
        // Busca el desarrollador por su ID
        Optional<Developer> developer = developerRepository.findById(id);
    
        if (developer.isPresent()) {
            // Si existe, elimina al desarrollador y retorna true
            developerRepository.deleteById(id); 
            return true;
        } else {
            // Si no existe, lanza una excepción con un mensaje
            throw new IllegalArgumentException("No existe ningún developer con el ID: " + id);
        }
    }

    @Override
    public Developer findById(Integer developerId) {
        // Devuelve el desarrollador si existe, de lo contrario retorna null
        return developerRepository.findById(developerId).orElse(null);
    }
}

