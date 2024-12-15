package com.sprinthub.sprinthub.repositories;


import com.sprinthub.sprinthub.models.ProjectJPA;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<ProjectJPA, Long> {
    List<ProjectJPA> findByUserId(UUID userId);
}
