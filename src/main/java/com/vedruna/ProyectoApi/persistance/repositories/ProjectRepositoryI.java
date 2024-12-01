package com.vedruna.ProyectoApi.persistance.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vedruna.ProyectoApi.DTO.ProjectDTO;
import com.vedruna.ProyectoApi.persistance.models.Project;

@Repository
public interface ProjectRepositoryI extends JpaRepository<Project, Integer> {
    public Optional<Project> findByName(String name);
    Page<Project> findAll(Pageable pageable);
    @Query("SELECT p FROM Project p JOIN p.technologies t WHERE t.name = :techName")
    List<ProjectDTO> findProjectsByTechnology(String techName);
    
} 