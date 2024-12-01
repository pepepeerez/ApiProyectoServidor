package com.vedruna.ProyectoApi.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.vedruna.ProyectoApi.DTO.ProjectDTO;
import com.vedruna.ProyectoApi.persistance.models.Project;

public interface ProjectServiceI {

    Page<ProjectDTO> showAllProjects(int page, int size); 
    ProjectDTO showProjectByName(String name);
    void saveProject(Project project);
    boolean deleteProject(Integer id);
    boolean moveProjectToTesting(Integer id);
    boolean moveProjectToProduction(Integer id);
    boolean updateProject(Integer id, Project project);
    Project findById(Integer projectId);
    List<ProjectDTO> getProjectsByTechnology(String techName);

    
} 
