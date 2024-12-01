package com.vedruna.ProyectoApi.DTO;

import java.sql.Date;
import java.util.List;

import com.vedruna.ProyectoApi.persistance.models.Developer;
import com.vedruna.ProyectoApi.persistance.models.Project;
import com.vedruna.ProyectoApi.persistance.models.Technology;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {

    private int id; // ID único del proyecto
    private String name; // Nombre del proyecto
    private String description; // Descripción del proyecto
    private Date start_date; // Fecha de inicio del proyecto
    private Date end_date; // Fecha de finalización del proyecto
    private String repository_url; // URL del repositorio del proyecto
    private String demo_url; // URL de la demostración del proyecto
    private String picture; // Imagen representativa del proyecto
    private String stateProjectName; // Nombre del estado del proyecto
    private List<TechnologyDTO> technologies; // Tecnologías asociadas al proyecto
    private List<DeveloperDTO> developers; // Desarrolladores asociados al proyecto

    // Constructor para convertir un objeto Project a ProjectDTO
    public ProjectDTO(Project p) {
        this.id = p.getId();
        this.name = p.getName();
        this.description = p.getDescription();
        this.start_date = p.getStart_date();
        this.end_date = p.getEnd_date();
        this.repository_url = p.getRepository_url();
        this.demo_url = p.getDemo_url();
        this.picture = p.getPicture();
        this.stateProjectName = p.getStateProject() != null ? p.getStateProject().getName() : null; // Asignar nombre del estado
        this.technologies = technologiesDTO(p.getTechnologies());
        this.developers = developersDTO(p.getDevelopers());
    }

    // Convierte una lista de objetos Technology a una lista de objetos TechnologyDTO
    public List<TechnologyDTO> technologiesDTO(List<Technology> technologies) {
        List<TechnologyDTO> technologiesRegistered = new ArrayList<>();
        for (Technology t : technologies) {
            technologiesRegistered.add(new TechnologyDTO(t));
        }
        return technologiesRegistered;
    }

    // Convierte una lista de objetos Developer a una lista de objetos DeveloperDTO
    public List<DeveloperDTO> developersDTO(List<Developer> developers) {
        List<DeveloperDTO> developersRegistered = new ArrayList<>();
        for (Developer d : developers) {
            developersRegistered.add(new DeveloperDTO(d));
        }
        return developersRegistered;
    }
}

