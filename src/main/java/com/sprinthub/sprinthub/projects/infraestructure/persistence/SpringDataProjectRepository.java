package com.sprinthub.sprinthub.projects.infraestructure.persistence;

import com.sprinthub.sprinthub.projects.domain.models.ProjectJPA;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SpringDataProjectRepository extends JpaRepository<ProjectJPA, UUID> {
    List<ProjectJPA> findByUserId(UUID userId);

}
