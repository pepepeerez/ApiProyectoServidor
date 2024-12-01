package com.vedruna.ProyectoApi.DTO;


import java.util.List;
import java.util.stream.Collectors;

import com.vedruna.ProyectoApi.persistance.models.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StateDTO {

  
    private int id;
    private String name;
    private List<ProjectDTO> statesWithProject;

    public StateDTO(Status s) {
        this.id = s.getId();
        this.name = s.getName();
        this.statesWithProject = s.getStatesWithProject() != null ? s.getStatesWithProject().stream()
            .map(ProjectDTO::new)
            .collect(Collectors.toList()) : null;
    }
    
}
