package com.sprinthub.sprinthub.projects.application.usecases;

import com.sprinthub.sprinthub.domain.models.UserJPA;
import com.sprinthub.sprinthub.domain.repository.UserRepository;
import com.sprinthub.sprinthub.projects.adapters.mappers.ProjectMapper;
import com.sprinthub.sprinthub.projects.application.dto.CreateProjectDTO;
import com.sprinthub.sprinthub.projects.application.dto.ProjectDTO;
import com.sprinthub.sprinthub.projects.domain.model.ProjectJPA;
import com.sprinthub.sprinthub.projects.domain.repository.ProjectRepository;

import java.util.UUID;

public class CreateProjectUseCase {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;

    public CreateProjectUseCase(ProjectRepository projectRepository, UserRepository userRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.projectMapper = projectMapper;
    }

    public ProjectDTO execute(UUID userId, CreateProjectDTO project) {
        UserJPA user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        ProjectJPA projectJPA = projectMapper.fromCreateDTO(project);
        projectJPA.setUser(user);
        projectRepository.save(projectJPA);
        return projectMapper.toDTO(projectJPA);
    }
}
