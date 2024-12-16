package com.sprinthub.sprinthub.projects.application.usecases;

import com.sprinthub.sprinthub.projects.adapters.mappers.ProjectMapper;
import com.sprinthub.sprinthub.projects.application.dto.ProjectDTO;
import com.sprinthub.sprinthub.projects.application.dto.UpdateProjectDTO;
import com.sprinthub.sprinthub.projects.domain.model.ProjectJPA;
import com.sprinthub.sprinthub.projects.domain.repository.ProjectRepository;

import java.util.UUID;

public class UpdateProjectUseCase {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public UpdateProjectUseCase(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    public ProjectDTO execute(UUID projectId, UUID userId, UpdateProjectDTO updatedProject) {
        ProjectJPA projectJPA = projectRepository.findById(projectId)
                .filter(project -> project.getUser().getId().equals(userId))
                .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado o no autorizado"));
        projectMapper.fromUpdateDTO(updatedProject, projectJPA);
        return projectMapper.toDTO(projectJPA);
    }
}
