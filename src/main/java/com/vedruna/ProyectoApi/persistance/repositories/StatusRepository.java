package com.vedruna.ProyectoApi.persistance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.qos.logback.core.status.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Integer> {

    
} 
    

