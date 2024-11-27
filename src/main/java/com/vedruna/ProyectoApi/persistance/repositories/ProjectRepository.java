package com.vedruna.ProyectoApi.persistance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vedruna.ProyectoApi.persistance.models.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    
}
