package com.sprinthub.sprinthub.services.imp;

import com.sprinthub.sprinthub.dtos.CreateProjectDTO;
import com.sprinthub.sprinthub.dtos.ProjectDTO;
import com.sprinthub.sprinthub.dtos.ProjectMapper;
import com.sprinthub.sprinthub.dtos.UpdateProjectDTO;
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
    public ProjectDTO createProject(UUID userId, CreateProjectDTO project) {
        UserJPA user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        ProjectJPA projectJPA = projectMapper.fromCreateDTO(project);
        projectJPA.setUser(user);
        projectRepository.save(projectJPA);
        return projectMapper.toDTO(projectJPA);
    }

    @Override
    public ProjectDTO getProjectById(UUID projectId, UUID userId) {
        ProjectJPA projectJPA = projectRepository.findById(projectId)
                .filter(project -> project.getUser().getId().equals(userId))
                .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado o no autorizado"));

        return projectMapper.toDTO(projectJPA);
    }


    @Override
    public List<ProjectDTO> getAllProjectsByUser(UUID userId) {
        List<ProjectJPA> projects = projectRepository.findByUserId(userId);
        return projects.stream()
                .map(projectMapper::toDTO)
                .toList();
    }

    @Override
    public ProjectDTO updateProject(UUID projectId, UUID userId, UpdateProjectDTO updatedProject) {
        ProjectJPA projectJPA = projectRepository.findById(projectId)
                .filter(project -> project.getUser().getId().equals(userId))
                .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado o no autorizado"));
        projectMapper.fromUpdateDTO(updatedProject, projectJPA);
        return projectMapper.toDTO(projectJPA);
    }

    @Override
    public void deleteProject(UUID projectId, UUID userId) {
        ProjectJPA project = projectRepository.findById(projectId)
                .filter(p -> p.getUser().getId().equals(userId))
                .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado o no autorizado"));
        projectRepository.delete(project);
    }
}
