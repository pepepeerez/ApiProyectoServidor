package com.vedruna.ProyectoApi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vedruna.ProyectoApi.persistance.models.Project;
import com.vedruna.ProyectoApi.persistance.models.Technology;
import com.vedruna.ProyectoApi.persistance.repositories.ProjectRepositoryI;
import com.vedruna.ProyectoApi.persistance.repositories.TechnologyRepositoryI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TechnologyServiceImpl implements TechnologyServiceI {

    @Autowired
    TechnologyRepositoryI technologyRepository;

    @Autowired
    ProjectRepositoryI projectRepository;

    // Guarda una tecnología con los proyectos asociados
    @Override
    public void saveTechnology(Technology technology) {
        // Verifica si ya existe una tecnología con el mismo ID
        if (technologyRepository.existsById(technology.getId())) {
            throw new IllegalArgumentException("El ID de la tecnología ya está en uso");
        }

        List<Project> managedProjects = new ArrayList<>();

        // Verifica que todos los proyectos asociados existan
        for (Project project : technology.getProjectsTechnologies()) {
            projectRepository.findById(project.getId()).ifPresentOrElse(
                managedProjects::add,
                () -> { 
                    throw new IllegalArgumentException("No existe ningún proyecto con el ID: " + project.getId());
                }
            );
        }

        // Asocia los proyectos gestionados a la tecnología
        technology.setProjectsTechnologies(managedProjects);

        // Guarda la tecnología con los proyectos asociados
        technologyRepository.save(technology);
    }

    // Elimina una tecnología por su ID
    @Override
    public boolean deleteTechnology(Integer id) {
        Optional<TechnologyRepositoryI> technology = technologyRepository.findById(id);

        if (technology.isPresent()) {
            technologyRepository.deleteById(id); 
            return true;
        } else {
            throw new IllegalArgumentException("No existe ninguna tecnología con el ID: " + id);
        }
    }

    // Busca una tecnología por su ID
    @Override
    public Technology findById(Integer techId) {
        return technologyRepository.findById(techId).orElse(null); 
    }

    // Asocia una tecnología con un proyecto
    @Override
    public void associateTechnologyWithProject(int projectId, int technologyId) {
        // Busca la tecnología por su ID
        Technology technology = technologyRepository.findById(technologyId).orElseThrow(() -> 
            new IllegalArgumentException("No se encontró una tecnología con el ID: " + technologyId));

        // Busca el proyecto por su ID
        Project project = projectRepository.findById(projectId).orElseThrow(() -> 
            new IllegalArgumentException("No se encontró un proyecto con el ID: " + projectId));

        // Asocia la tecnología con el proyecto
        project.getTechnologies().add(technology);
        technology.getProjectsTechnologies().add(project);

        // Guarda los cambios en el proyecto y la tecnología
        projectRepository.save(project);
        technologyRepository.save(technology);
    }
}

