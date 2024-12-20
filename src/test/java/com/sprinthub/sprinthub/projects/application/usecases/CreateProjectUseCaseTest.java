package com.sprinthub.sprinthub.projects.application.usecases;

import com.sprinthub.sprinthub.users.domain.models.UserJPA;
import com.sprinthub.sprinthub.users.infraestructure.persistence.SpringDataUserRepository;
import com.sprinthub.sprinthub.projects.application.mappers.ProjectMapper;
import com.sprinthub.sprinthub.projects.application.dto.CreateProjectDTO;
import com.sprinthub.sprinthub.projects.application.dto.ProjectDTO;
import com.sprinthub.sprinthub.projects.domain.models.ProjectJPA;
import com.sprinthub.sprinthub.projects.domain.repository.ProjectRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class CreateProjectUseCaseTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private SpringDataUserRepository userRepository;

    @Mock
    private ProjectMapper projectMapper;

    @InjectMocks
    private CreateProjectUseCase createProjectUseCase;

    @Test
    void shouldCreateProjectSuccessfully() {
        // Datos de prueba
        UUID userId = UUID.randomUUID();
        CreateProjectDTO createDTO = CreateProjectDTO.builder().name("Nuevo Proyecto").description("Descripción").build();
        ProjectJPA project = new ProjectJPA();
        project.setName("Nuevo Proyecto");
        project.setDescription("Descripción");
        UserJPA user = new UserJPA();
        user.setId(userId);
        project.setUser(user);
        ProjectDTO expectedDTO = new ProjectDTO(UUID.randomUUID(), "Nuevo Proyecto", "Descripción");

        // Configurar mocks
        Mockito.when(projectMapper.fromCreateDTO(createDTO)).thenReturn(project);
        Mockito.when(projectRepository.save(project)).thenReturn(project);
        Mockito.when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));
        Mockito.when(projectMapper.toDTO(project)).thenReturn(expectedDTO);

        // Ejecución
        ProjectDTO result = createProjectUseCase.execute(userId, createDTO);
        Assertions.assertEquals(expectedDTO, result);
    }

    /*
    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
        // Datos de prueba
        UUID userId = UUID.randomUUID();
        CreateProjectDTO createDTO = new CreateProjectDTO("", "Descripción");

        // Ejecución y verificación de la excepción
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            createProjectUseCase.execute(userId, createDTO);
        });

        assertEquals("El nombre del proyecto no puede estar vacío", exception.getMessage());

        // Verificar que no se interactuó con el mapper ni el repositorio
        verifyNoInteractions(projectMapper);
        verifyNoInteractions(projectRepository);
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        // Datos de prueba
        UUID userId = UUID.randomUUID();
        CreateProjectDTO createDTO = new CreateProjectDTO(null, "Descripción");

        // Ejecución y verificación de la excepción
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            createProjectUseCase.execute(userId, createDTO);
        });

        assertEquals("El nombre del proyecto no puede estar vacío", exception.getMessage());

        // Verificar que no se interactuó con el mapper ni el repositorio
        verifyNoInteractions(projectMapper);
        verifyNoInteractions(projectRepository);
    }

     */
}