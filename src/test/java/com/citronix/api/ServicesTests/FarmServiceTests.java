package com.citronix.api.ServicesTests;

import com.citronix.api.domains.Farm;
import com.citronix.api.repository.FarmRepository;
import com.citronix.api.service.implementations.FarmServiceImpl;
import com.citronix.api.DTO.FarmDto;
import com.citronix.api.exception.EntityAlreadyExistsException;
import com.citronix.api.exception.EntityNotFoundException;
import com.citronix.api.mapper.FarmMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class FarmServiceTests {

    @Mock
    private FarmRepository farmRepository;

    @Mock
    private FarmMapper farmMapper;

    @InjectMocks
    private FarmServiceImpl farmService;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateFarm_Success() {
        // Arrange
        FarmDto farmDto = new FarmDto("Citron Farm", "California", 100.0, null);
        Farm farm = Farm.builder()
                .id(1L)
                .name("Citron Farm")
                .location("California")
                .area(100.0)
                .build();

        when(farmMapper.toFarm(farmDto)).thenReturn(farm);
        when(farmRepository.save(farm)).thenReturn(farm);
        when(farmMapper.toDto(farm)).thenReturn(farmDto);

        // Act
        Farm createdFarm = farmService.create(farmDto);

        // Assert
        assertEquals(farm.getName(), createdFarm.getName());
        verify(farmRepository, times(1)).save(farm);
    }

    @Test
    void testCreateFarm_AlreadyExists() {
        // Arrange
        FarmDto farmDto = new FarmDto("Citron Farm", "California", 100.0, null);

        when(farmRepository.existsByName("Citron Farm")).thenReturn(true);

        // Act & Assert
        assertThrows(EntityAlreadyExistsException.class, () -> farmService.create(farmDto));
        verify(farmRepository, never()).save(any());
    }

    @Test
    void testFindFarmById_Success() {
        // Arrange
        Farm farm = Farm.builder()
                .id(1L)
                .name("Citron Farm")
                .location("California")
                .area(100.0)
                .build();

        when(farmRepository.findById(1L)).thenReturn(Optional.of(farm));

        // Act
        Farm foundFarm = farmService.findById(1L);

        // Assert
        assertEquals(farm.getId(), foundFarm.getId());
        verify(farmRepository, times(1)).findById(1L);
    }

    @Test
    void testFindFarmById_NotFound() {
        // Arrange
        when(farmRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> farmService.findById(1L));
        verify(farmRepository, times(1)).findById(1L);
    }

//    @Test
//    void testDeleteFarm_Success() {
//        // Arrange
//        when(farmRepository.existsById(1L)).thenReturn(true);
//
//        // Act
//        farmService.delete(1L);
//
//        // Assert
//        verify(farmRepository, times(1)).deleteById(1L);
//    }

    @Test
    void testDeleteFarm_NotFound() {
        // Arrange
        when(farmRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> farmService.delete(1L));
        verify(farmRepository, never()).deleteById(anyLong());
    }

    @Test
    void testUpdateFarm_Success() {
        // Arrange
        FarmDto farmDto = new FarmDto("Updated Farm", "New York", 200.0, null);
        Farm existingFarm = Farm.builder()
                .id(1L)
                .name("Citron Farm")
                .location("California")
                .area(100.0)
                .build();
        Farm updatedFarm = Farm.builder()
                .id(1L)
                .name("Updated Farm")
                .location("New York")
                .area(200.0)
                .build();

        when(farmRepository.findById(1L)).thenReturn(Optional.of(existingFarm));
        when(farmMapper.toFarm(farmDto)).thenReturn(updatedFarm);
        when(farmRepository.save(updatedFarm)).thenReturn(updatedFarm);

        // Act
        Farm result = farmService.update(1L, farmDto);

        // Assert
        assertEquals(updatedFarm.getName(), result.getName());
        verify(farmRepository, times(1)).save(updatedFarm);
    }

    @Test
    void testUpdateFarm_NotFound() {
        // Arrange
        FarmDto farmDto = new FarmDto("Updated Farm", "New York", 200.0, null);
        when(farmRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> farmService.update(1L, farmDto));
        verify(farmRepository, never()).save(any());
    }

    @Test
    void testFindAllFarms() {
        // Arrange
        Farm farm1 = Farm.builder().id(1L).name("Farm 1").location("Location 1").area(50.0).build();
        Farm farm2 = Farm.builder().id(2L).name("Farm 2").location("Location 2").area(75.0).build();
        List<Farm> farms = List.of(farm1, farm2);

        when(farmRepository.findAll()).thenReturn(farms);

        // Act
        List<Farm> result = farmService.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(farmRepository, times(1)).findAll();
    }
}
