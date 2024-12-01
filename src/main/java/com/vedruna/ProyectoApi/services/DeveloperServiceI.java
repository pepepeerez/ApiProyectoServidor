package com.vedruna.ProyectoApi.services;

import com.vedruna.ProyectoApi.persistance.models.Developer;

public interface DeveloperServiceI {

    void saveDeveloper(Developer developer);
    boolean deleteDeveloper(Integer id);
    Developer findById(Integer developerId);



    
}
