package com.vedruna.ProyectoApi.DTO;

import java.lang.Thread.State;
import java.util.List;
import java.util.stream.Collectors;

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

    public StateDTO(State s) {
        this.id = s.getId();
        this.name = s.getName();
        this.statesWithProject = s.getStatesWithProject() != null ? s.getStatesWithProject().stream()
            .map(ProjectDTO::new)
            .collect(Collectors.toList()) : null;
    }
    
}
