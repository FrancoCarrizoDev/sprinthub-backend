package com.sprinthub.sprinthub.services.imp;

import com.sprinthub.sprinthub.dtos.ProjectDTO;
import com.sprinthub.sprinthub.dtos.ProjectMapper;
import com.sprinthub.sprinthub.models.ProjectJPA;
import com.sprinthub.sprinthub.models.UserJPA;
import com.sprinthub.sprinthub.repositories.ProjectRepository;
import com.sprinthub.sprinthub.repositories.UserRepository;
import com.sprinthub.sprinthub.services.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;

    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.projectMapper = projectMapper;
    }

    @Override
    public ProjectJPA createProject(Long userId, ProjectJPA project) {
        UserJPA user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        project.setUser(user);
        return projectRepository.save(project);
    }

    @Override
    public ProjectJPA getProjectById(Long projectId, Long userId) {
        return projectRepository.findById(projectId)
                .filter(project -> project.getUser().getId().equals(userId))
                .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado o no autorizado"));
    }

    @Override
    public List<ProjectDTO> getAllProjectsByUser(UUID userId) {
        List<ProjectJPA> projects = projectRepository.findByUserId(userId);
        return projects.stream()
                .map(projectMapper::toDTO)
                .toList();
    }

    @Override
    public ProjectJPA updateProject(Long projectId, Long userId, ProjectJPA updatedProject) {
        ProjectJPA project = getProjectById(projectId, userId);
        project.setName(updatedProject.getName());
        project.setDescription(updatedProject.getDescription());
        return projectRepository.save(project);
    }

    @Override
    public void deleteProject(Long projectId, Long userId) {
        ProjectJPA project = getProjectById(projectId, userId);
        projectRepository.delete(project);
    }
}
