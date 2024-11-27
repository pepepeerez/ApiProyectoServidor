package com.vedruna.ProyectoApi.persistance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Technology extends JpaRepository<Technology, Integer> {

    
} 
    
