package com.sprinthub.sprinthub.projects.infraestructure.persistence;

import com.sprinthub.sprinthub.projects.domain.models.ProjectJPA;
import com.sprinthub.sprinthub.projects.domain.repository.ProjectRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaProjectRepository implements ProjectRepository {

    private final SpringDataProjectRepository springDataRepository;

    public JpaProjectRepository(SpringDataProjectRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public ProjectJPA save(ProjectJPA project) {
        return springDataRepository.save(project);
    }

    @Override
    public Optional<ProjectJPA> findById(UUID id) {
        return springDataRepository.findById(id);
    }

    @Override
    public List<ProjectJPA> findByUserId(UUID userId) {
        return springDataRepository.findByUserId(userId);
    }

    @Override
    public void deleteById(UUID id) {
        springDataRepository.deleteById(id);
    }
}