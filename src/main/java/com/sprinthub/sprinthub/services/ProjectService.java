package com.sprinthub.sprinthub.services;

import com.sprinthub.sprinthub.dtos.ProjectDTO;
import com.sprinthub.sprinthub.models.ProjectJPA;

import java.util.List;
import java.util.UUID;

public interface ProjectService {
    ProjectJPA createProject(Long userId, ProjectJPA project);
    ProjectJPA getProjectById(Long projectId, Long userId);
    List<ProjectDTO> getAllProjectsByUser(UUID userId);
    ProjectJPA updateProject(Long projectId, Long userId, ProjectJPA project);
    void deleteProject(Long projectId, Long userId);
}