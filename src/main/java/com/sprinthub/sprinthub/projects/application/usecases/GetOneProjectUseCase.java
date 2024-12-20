package com.sprinthub.sprinthub.projects.application.usecases;

import com.sprinthub.sprinthub.projects.application.mappers.ProjectMapper;
import com.sprinthub.sprinthub.projects.application.dto.ProjectDTO;
import com.sprinthub.sprinthub.projects.domain.models.ProjectJPA;
import com.sprinthub.sprinthub.projects.domain.repository.ProjectRepository;

import java.util.UUID;

public class GetOneProjectUseCase {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public GetOneProjectUseCase(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    public ProjectDTO execute(UUID projectId, UUID userId) {
        ProjectJPA projectJPA = projectRepository.findById(projectId)
                .filter(project -> project.getUser().getId().equals(userId))
                .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado o no autorizado"));

        return projectMapper.toDTO(projectJPA);
    }
}
