package com.sprinthub.sprinthub.projects.application.mappers;

import com.sprinthub.sprinthub.projects.domain.models.ProjectJPA;
import com.sprinthub.sprinthub.projects.application.dto.CreateProjectDTO;
import com.sprinthub.sprinthub.projects.application.dto.ProjectDTO;
import com.sprinthub.sprinthub.projects.application.dto.UpdateProjectDTO;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {


    public ProjectDTO toDTO(ProjectJPA project) {
        return new ProjectDTO(
                project.getId(),
                project.getName(),
                project.getDescription()
        );
    }


    public ProjectJPA fromDTO(ProjectDTO dto) {
        ProjectJPA project = new ProjectJPA();
        project.setId(dto.getId());
        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        return project;
    }


    public ProjectJPA fromCreateDTO(CreateProjectDTO dto) {
        ProjectJPA project = new ProjectJPA();
        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        return project;
    }


    public ProjectJPA fromUpdateDTO(UpdateProjectDTO dto, ProjectJPA existingProject) {
        existingProject.setName(dto.getName());
        existingProject.setDescription(dto.getDescription());
        return existingProject;
    }
}
