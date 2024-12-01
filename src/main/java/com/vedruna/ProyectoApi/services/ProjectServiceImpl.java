package com.vedruna.ProyectoApi.services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vedruna.ProyectoApi.DTO.ProjectDTO;
import com.vedruna.ProyectoApi.persistance.models.Project;
import com.vedruna.ProyectoApi.persistance.models.Status;
import com.vedruna.ProyectoApi.persistance.repositories.ProjectRepositoryI;
import com.vedruna.ProyectoApi.persistance.repositories.StatusRepository;

@Service
public class ProjectServiceImpl implements ProjectServiceI {

    @Autowired
    ProjectRepositoryI projectRepository;

    @Autowired
    StatusRepository stateRepository;

    // Obtiene todos los proyectos paginados
    @Override
    public Page<ProjectDTO> showAllProjects(int page, int size) {
        Pageable pageable = PageRequest.of(page, size); // Configura la paginación
        Page<Project> projectPage = projectRepository.findAll(pageable); // Obtiene los proyectos de la base de datos
        return projectPage.map(ProjectDTO::new); // Convierte los proyectos a DTO
    }

    // Busca un proyecto por su nombre (parcial o completo)
    @Override
    public ProjectDTO showProjectByName(String name) {
        List<Project> projects = projectRepository.findAll();
        Project project = null;
        for (Project p : projects) {
            if (p.getName().contains(name)) {
                project = p;
                break;
            }
        }
        if (project == null) {
            throw new IllegalArgumentException("No se encontró ningún proyecto con un nombre que contenga: " + name);
        }
        return new ProjectDTO(project);
    }

    // Guarda un proyecto en la base de datos
    @Override
    public void saveProject(Project project) {
        projectRepository.save(project);
    }

    // Elimina un proyecto por su ID
    @Override
    public boolean deleteProject(Integer id) {
        Optional<Project> project = projectRepository.findById(id);

        if (project.isPresent()) {
            projectRepository.deleteById(id);
            return true;
        } else {
            throw new IllegalArgumentException("No existe ningún proyecto con el ID: " + id);
        }
    }

    // Actualiza un proyecto existente
    @Override
    public boolean updateProject(Integer id, Project project) {
        Optional<Project> projectToUpdate = projectRepository.findById(id);

        if (projectToUpdate.isPresent()) {
            Project existingProject = projectToUpdate.get();
            // Actualiza los datos del proyecto
            existingProject.setName(project.getName());
            existingProject.setDescription(project.getDescription());
            existingProject.setStart_date(project.getStart_date());
            existingProject.setEnd_date(project.getEnd_date());
            existingProject.setRepository_url(project.getRepository_url());
            existingProject.setDemo_url(project.getDemo_url());
            existingProject.setPicture(project.getPicture());
            existingProject.setTechnologies(project.getTechnologies());
            existingProject.setDevelopers(project.getDevelopers());
            projectRepository.save(existingProject); // Guarda los cambios
            return true;
        } else {
            return false;
        }
    }

    // Mueve un proyecto al estado "testing"
    @Override
    public boolean moveProjectToTesting(Integer id) {
        Optional<Project> projectOptional = projectRepository.findById(id);
        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();
            Optional<Status> stateOptional = stateRepository.findById(2); // Busca el estado "testing"
            if (stateOptional.isPresent()) {
                project.setStateProject(stateOptional.get());
                projectRepository.save(project);
                return true;
            } else {
                throw new IllegalArgumentException("No existe un estado con el ID 2.");
            }
        } else {
            throw new IllegalArgumentException("No existe un proyecto con el ID: " + id);
        }
    }

    // Mueve un proyecto al estado "producción"
    @Override
    public boolean moveProjectToProduction(Integer id) {
        Optional<Project> projectOptional = projectRepository.findById(id);
        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();
            Optional<Status> stateOptional = stateRepository.findById(3); // Busca el estado "producción"
            if (stateOptional.isPresent()) {
                project.setStateProject(stateOptional.get());
                projectRepository.save(project);
                return true;
            } else {
                throw new IllegalArgumentException("No existe un estado con el ID 3.");
            }
        } else {
            throw new IllegalArgumentException("No existe un proyecto con el ID: " + id);
        }
    }

    // Busca un proyecto por su ID
    @Override
    public Project findById(Integer projectId) {
        return projectRepository.findById(projectId).orElse(null);
    }

    // Obtiene una lista de proyectos que utilizan una tecnología específica
    @Override
    public List<ProjectDTO> getProjectsByTechnology(String techName) {
        return projectRepository.findProjectsByTechnology(techName);
    }
}

