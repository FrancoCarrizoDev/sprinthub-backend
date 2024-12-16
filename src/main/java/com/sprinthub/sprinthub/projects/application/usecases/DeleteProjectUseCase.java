package com.sprinthub.sprinthub.projects.application.usecases;

import com.sprinthub.sprinthub.projects.domain.repository.ProjectRepository;

import java.util.UUID;

public class DeleteProjectUseCase {

    private final ProjectRepository projectRepository;

    public DeleteProjectUseCase(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public void execute(UUID projectId) {
        projectRepository.deleteById(projectId);
    }

}
