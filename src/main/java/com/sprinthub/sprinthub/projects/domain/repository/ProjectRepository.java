package com.sprinthub.sprinthub.projects.domain.repository;


import com.sprinthub.sprinthub.projects.domain.model.ProjectJPA;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectRepository {
    void save(ProjectJPA project);
    Optional<ProjectJPA> findById(UUID id);
    List<ProjectJPA> findByUserId(UUID userId);
    void deleteById(UUID id);
}