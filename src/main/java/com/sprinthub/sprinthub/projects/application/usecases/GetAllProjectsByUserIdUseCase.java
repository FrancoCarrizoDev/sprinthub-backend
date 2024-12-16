package com.sprinthub.sprinthub.projects.application.usecases;

import com.sprinthub.sprinthub.projects.adapters.mappers.ProjectMapper;
import com.sprinthub.sprinthub.projects.application.dto.ProjectDTO;
import com.sprinthub.sprinthub.projects.domain.model.ProjectJPA;
import com.sprinthub.sprinthub.projects.domain.repository.ProjectRepository;

import java.util.List;
import java.util.UUID;

public class GetAllProjectsByUserIdUseCase {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public GetAllProjectsByUserIdUseCase(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }


    public List<ProjectDTO> execute(UUID userId) {
        List<ProjectJPA> projects = projectRepository.findByUserId(userId);
        return projects.stream()
                .map(projectMapper::toDTO)
                .toList();
    }
}
