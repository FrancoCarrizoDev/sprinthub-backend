package com.sprinthub.sprinthub.projects.infraestructure.config;

import com.sprinthub.sprinthub.domain.repository.UserRepository;
import com.sprinthub.sprinthub.projects.adapters.mappers.ProjectMapper;
import com.sprinthub.sprinthub.projects.application.usecases.*;
import com.sprinthub.sprinthub.projects.domain.repository.ProjectRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;

    public UseCaseConfig(ProjectRepository projectRepository, ProjectMapper projectMapper, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.userRepository = userRepository;
    }

    @Bean
    public CreateProjectUseCase createProjectUseCase() {
        return new CreateProjectUseCase(projectRepository, userRepository, projectMapper);
    }

    @Bean
    public GetOneProjectUseCase getOneProjectUseCase(ProjectRepository projectRepository) {
        return new GetOneProjectUseCase(projectRepository, projectMapper);
    }

    @Bean
    public GetAllProjectsByUserIdUseCase getAllProjectsByUserIdUseCase(ProjectRepository projectRepository) {
        return new GetAllProjectsByUserIdUseCase(projectRepository, projectMapper);
    }

    @Bean
    public UpdateProjectUseCase updateProjectUseCase(ProjectRepository projectRepository) {
        return new UpdateProjectUseCase(projectRepository, projectMapper);
    }

    @Bean
    public DeleteProjectUseCase deleteProjectUseCase(ProjectRepository projectRepository) {
        return new DeleteProjectUseCase(projectRepository);
    }
}