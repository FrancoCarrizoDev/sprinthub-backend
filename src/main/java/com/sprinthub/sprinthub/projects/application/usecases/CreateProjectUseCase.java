package com.sprinthub.sprinthub.projects.application.usecases;

import com.sprinthub.sprinthub.users.domain.models.User;
import com.sprinthub.sprinthub.users.infraestructure.entities.UserMapper;
import com.sprinthub.sprinthub.users.infraestructure.entities.UserEntity;
import com.sprinthub.sprinthub.users.domain.repository.UserRepository;
import com.sprinthub.sprinthub.projects.application.mappers.ProjectMapper;
import com.sprinthub.sprinthub.projects.application.dto.CreateProjectDTO;
import com.sprinthub.sprinthub.projects.application.dto.ProjectDTO;
import com.sprinthub.sprinthub.projects.domain.models.ProjectJPA;
import com.sprinthub.sprinthub.projects.domain.repository.ProjectRepository;

import java.util.UUID;

public class CreateProjectUseCase {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;

    private final UserMapper userMapper;

    public CreateProjectUseCase(ProjectRepository projectRepository, UserRepository userRepository, ProjectMapper projectMapper, UserMapper userMapper) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.projectMapper = projectMapper;
        this.userMapper = userMapper;
    }

    public ProjectDTO execute(UUID userId, CreateProjectDTO project) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        ProjectJPA projectJPA = projectMapper.fromCreateDTO(project);
        UserEntity userEntity = userMapper.toEntity(user);
        projectJPA.setUser(userEntity);
        ProjectJPA savedProject = projectRepository.save(projectJPA);
        return projectMapper.toDTO(savedProject);
    }
}
