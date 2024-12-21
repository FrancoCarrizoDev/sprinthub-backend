package com.sprinthub.sprinthub.projects.adapters.controllers.in;

import com.sprinthub.sprinthub.projects.application.dto.CreateProjectDTO;
import com.sprinthub.sprinthub.projects.application.dto.ProjectDTO;
import com.sprinthub.sprinthub.projects.application.dto.UpdateProjectDTO;
import com.sprinthub.sprinthub.projects.application.usecases.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final CreateProjectUseCase createProjectUseCase;
    private final GetOneProjectUseCase getOneProjectUseCase;
    private final GetAllProjectsByUserIdUseCase getAllProjectsByUserIdUseCase;
    private final UpdateProjectUseCase updateProjectUseCase;
    private final DeleteProjectUseCase deleteProjectUsecase;


    public ProjectController(CreateProjectUseCase createProjectUseCase, GetOneProjectUseCase getOneProjectUseCase, GetAllProjectsByUserIdUseCase getAllProjectsByUserIdUseCase, UpdateProjectUseCase updateProjectUseCase, DeleteProjectUseCase deleteProjectUsecase) {
        this.createProjectUseCase = createProjectUseCase;
        this.getOneProjectUseCase = getOneProjectUseCase;
        this.getAllProjectsByUserIdUseCase = getAllProjectsByUserIdUseCase;
        this.updateProjectUseCase = updateProjectUseCase;
        this.deleteProjectUsecase = deleteProjectUsecase;
    }


    @PostMapping
    public ResponseEntity<ProjectDTO> createProject(@RequestHeader("userId") UUID userId, @RequestBody CreateProjectDTO project) {
        return ResponseEntity.ok(createProjectUseCase.execute(userId, project));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@RequestHeader("userId") UUID userId, @PathVariable UUID id) {
        return ResponseEntity.ok(getOneProjectUseCase.execute(id, userId));
    }

    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getAllProjectsByUser(@RequestHeader("userId") UUID userId) {
        return ResponseEntity.ok(getAllProjectsByUserIdUseCase.execute(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> updateProject(@RequestHeader("userId") UUID userId, @PathVariable UUID id, @RequestBody UpdateProjectDTO project) {
        return ResponseEntity.ok(updateProjectUseCase.execute(id, userId, project));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@RequestHeader("userId") UUID userId, @PathVariable UUID id) {
        deleteProjectUsecase.execute(id);
        return ResponseEntity.noContent().build();
    }
}