package com.sprinthub.sprinthub.projects.application.usecases;

import com.sprinthub.sprinthub.users.infraestructure.entities.UserEntity;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        UserEntity user = new UserEntity();
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

    @Test
    void testCalculationFinalSpeed(){
        Assertions.assertEquals(75.0, calculateFinalSpeed(60.0, new int[] { 0, 30, 0, -45, 0 }));

    }

    public static double calculateFinalSpeed(double initialSpeed, int[] inclinations) {

        double currentSpeed = initialSpeed;

        for (int inclination : inclinations) {
            currentSpeed -= Math.abs(inclination) * (inclination > 0 ? 1 : -1);

            if (currentSpeed <= 0) {
                return 0;
            }
        }
        return currentSpeed;
    }

    public enum DiscountType {
        Standard,
        Seasonal,
        Weight;
    }


    public static double getDiscountedPrice(double cartWeight,
                                            double totalPrice,
                                            DiscountType discountType) {

        double discount = switch (discountType) {
            case Seasonal -> 12;
            case Weight -> cartWeight <= 10 ? 6 : 18;
            default -> 6;
        };

        return totalPrice * (1 - discount / 100.0);
    }

    @Test
    void testGetDiscountedPrice(){
        Assertions.assertEquals(82.0, getDiscountedPrice(12, 100, DiscountType.Weight));
    }


    public static boolean canTravelTo(boolean[][] gameMatrix, int fromRow, int fromColumn, int toRow, int toColumn) {
        // Verificar si las coordenadas de destino están dentro del tablero
        if (toRow < 0 || toRow >= gameMatrix.length || toColumn < 0 || toColumn >= gameMatrix[0].length) {
            return false;
        }

        // Verificar si el destino está en una dirección válida (horizontal o vertical)
        if (!(fromRow == toRow || fromColumn == toColumn)) {
            return false;
        }

        // Determinar la dirección del movimiento
        int rowStep = Integer.compare(toRow, fromRow); // 1, -1 o 0
        int colStep = Integer.compare(toColumn, fromColumn); // 1, -1 o 0

        // Recorrer el camino desde el punto inicial al destino y verificar si hay tierra
        int currentRow = fromRow;
        int currentColumn = fromColumn;

        while (currentRow != toRow || currentColumn != toColumn) {
            currentRow += rowStep;
            currentColumn += colStep;

            // Verificar si el camino está bloqueado por tierra
            if (!gameMatrix[currentRow][currentColumn]) {
                return false;
            }
        }

        return true; // Si llegamos aquí, el destino es alcanzable
    }
    @Test
    void testCanTravelTo(){
        boolean[][] gameMatrix = {
                {false, true,  true,  false, false, false},
                {true,  true,  true,  false, false, false},
                {true,  true,  true,  true,  true,  true},
                {false, true,  true,  false, true,  true},
                {false, true,  true,  true,  false, true},
                {false, false, false, false, false, false},
        };


        Assertions.assertTrue(canTravelTo(gameMatrix, 3, 2, 2, 2));
        Assertions.assertFalse(canTravelTo(gameMatrix, 3, 2, 3, 4));
        Assertions.assertFalse(canTravelTo(gameMatrix, 3, 2, 6, 2));

    }

    @Test
    void reverseString(){
        String input = "Hello, World!";
        String reversed = reverseString(input);

        System.out.println("Original: " + input);
        System.out.println("Reversed: " + reversed);
    }

    private static String reverseString(String input){
        char[] chars = input.toCharArray();
        int left = 0;
        int right = chars.length - 1;

        while(left < right){
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;

            left++;
            right--;
        }
        return new String(chars);
    }

}