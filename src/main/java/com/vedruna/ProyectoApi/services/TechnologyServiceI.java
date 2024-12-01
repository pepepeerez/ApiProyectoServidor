package com.vedruna.ProyectoApi.services;

import com.vedruna.ProyectoApi.persistance.models.Technology;

public interface TechnologyServiceI {

    void saveTechnology(Technology technology);
    boolean deleteTechnology(Integer id);
    Technology findById(Integer techId);
    void associateTechnologyWithProject(int projectId, int technologyId);



    
} 