package com.sprinthub.sprinthub.services;

import com.sprinthub.sprinthub.dtos.CreateProjectDTO;
import com.sprinthub.sprinthub.dtos.ProjectDTO;
import com.sprinthub.sprinthub.dtos.UpdateProjectDTO;
import com.sprinthub.sprinthub.models.ProjectJPA;

import java.util.List;
import java.util.UUID;

public interface ProjectService {
    ProjectDTO createProject(UUID userId, CreateProjectDTO project);
    ProjectDTO getProjectById(UUID projectId, UUID userId);

    List<ProjectDTO> getAllProjectsByUser(UUID userId);
    ProjectDTO updateProject(UUID projectId, UUID userId, UpdateProjectDTO project);
    void deleteProject(UUID projectId, UUID userId);
}