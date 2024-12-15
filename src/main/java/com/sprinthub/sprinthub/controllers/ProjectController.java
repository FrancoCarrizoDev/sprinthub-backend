package com.sprinthub.sprinthub.controllers;

import com.sprinthub.sprinthub.dtos.CreateProjectDTO;
import com.sprinthub.sprinthub.dtos.ProjectDTO;
import com.sprinthub.sprinthub.dtos.UpdateProjectDTO;
import com.sprinthub.sprinthub.models.ProjectJPA;
import com.sprinthub.sprinthub.services.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<ProjectDTO> createProject(@RequestHeader("userId") UUID userId, @RequestBody CreateProjectDTO project) {
        return ResponseEntity.ok(projectService.createProject(userId, project));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@RequestHeader("userId") UUID userId, @PathVariable UUID id) {
        return ResponseEntity.ok(projectService.getProjectById(id, userId));
    }

    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getAllProjectsByUser(@RequestHeader("userId") UUID userId) {
        return ResponseEntity.ok(projectService.getAllProjectsByUser(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> updateProject(@RequestHeader("userId") UUID userId, @PathVariable UUID id, @RequestBody UpdateProjectDTO project) {
        return ResponseEntity.ok(projectService.updateProject(id, userId, project));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@RequestHeader("userId") UUID userId, @PathVariable UUID id) {
        projectService.deleteProject(id, userId);
        return ResponseEntity.noContent().build();
    }
}