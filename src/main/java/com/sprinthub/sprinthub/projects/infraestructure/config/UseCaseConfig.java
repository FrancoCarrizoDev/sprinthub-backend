package com.sprinthub.sprinthub.projects.infraestructure.config;

import com.sprinthub.sprinthub.users.domain.models.UserMapper;
import com.sprinthub.sprinthub.users.domain.repository.UserRepository;
import com.sprinthub.sprinthub.projects.application.mappers.ProjectMapper;
import com.sprinthub.sprinthub.projects.application.usecases.*;
import com.sprinthub.sprinthub.projects.domain.repository.ProjectRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public GetOneProjectUseCase getOneProjectUseCase(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        return new GetOneProjectUseCase(projectRepository, projectMapper);
    }

    @Bean
    public GetAllProjectsByUserIdUseCase getAllProjectsByUserIdUseCase(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        return new GetAllProjectsByUserIdUseCase(projectRepository, projectMapper);
    }

    @Bean
    public UpdateProjectUseCase updateProjectUseCase(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        return new UpdateProjectUseCase(projectRepository, projectMapper);
    }

    @Bean
    public DeleteProjectUseCase deleteProjectUseCase(ProjectRepository projectRepository) {
        return new DeleteProjectUseCase(projectRepository);
    }

    @Bean
    public CreateProjectUseCase createProjectUseCase(ProjectRepository projectRepository, ProjectMapper projectMapper, UserRepository userRepository, UserMapper userMapper) {
        return new CreateProjectUseCase(projectRepository, userRepository, projectMapper, userMapper);
    }
}