package com.citronix.api.ServicesTests;

import com.citronix.api.DTO.FieldDto;
import com.citronix.api.domains.Field;
import com.citronix.api.domains.Farm;
import com.citronix.api.exception.EntityNotFoundException;
import com.citronix.api.mapper.FieldMapper;
import com.citronix.api.repository.FieldRepository;
import com.citronix.api.repository.FarmRepository;
import com.citronix.api.service.implementations.FieldServiceImpl;
import com.citronix.api.service.interfaces.FieldService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class FieldServiceTests {

    @Mock
    private FieldRepository fieldRepository;

    @Mock
    private FarmRepository farmRepository;

    @Mock
    private FieldMapper fieldMapper;

    @InjectMocks
    private FieldServiceImpl fieldService;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateField_Success() {
        // Arrange
        Long farmId = 1L;
        double fieldArea = 50.0;

        Farm farm = new Farm();
        farm.setId(farmId);
        farm.setArea(100.0);  // Total farm area

        FieldDto fieldDto = new FieldDto();
        fieldDto.setFarmId(farmId);
        fieldDto.setArea(fieldArea);
        fieldDto.setName("Test Field");

        Field field = new Field();
        field.setFarm(farm);
        field.setArea(fieldArea);
        field.setName("Test Field");

        when(farmRepository.findById(farmId)).thenReturn(Optional.of(farm));
        when(fieldRepository.findTotalAreaByFarmId(farmId)).thenReturn(40.0);  // Existing total area of the farm
        when(fieldMapper.toField(fieldDto)).thenReturn(field);
        when(fieldRepository.save(any(Field.class))).thenReturn(field);
        when(fieldMapper.toDto(field)).thenReturn(fieldDto);

        // Act
        FieldDto result = fieldService.create(fieldDto);

        // Assert
        assertNotNull(result);
        assertEquals("Test Field", result.getName());
        assertEquals(farmId, result.getFarmId());
        assertEquals(fieldArea, result.getArea());
    }

    @Test
    void testCreateField_ExceedsFarmArea() {
        // Arrange
        Long farmId = 1L;
        double fieldArea = 60.0; // Exceeds available farm area

        Farm farm = new Farm();
        farm.setId(farmId);
        farm.setArea(100.0);  // Total farm area

        FieldDto fieldDto = new FieldDto();
        fieldDto.setFarmId(farmId);
        fieldDto.setArea(fieldArea);
        fieldDto.setName("Test Field");

        when(farmRepository.findById(farmId)).thenReturn(Optional.of(farm));
        when(fieldRepository.findTotalAreaByFarmId(farmId)).thenReturn(50.0);  // Existing total area of the farm

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            fieldService.create(fieldDto);
        });

        assertEquals("The field's area exceeds the farm's remaining area. Available area: 50.0", thrown.getMessage());
    }

    @Test
    void testCreateField_FarmNotFound() {
        // Arrange
        Long farmId = 999L;  // Non-existent farm ID
        FieldDto fieldDto = new FieldDto();
        fieldDto.setFarmId(farmId);
        fieldDto.setArea(50.0);
        fieldDto.setName("Test Field");

        when(farmRepository.findById(farmId)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            fieldService.create(fieldDto);
        });

        assertEquals("Farm with ID 999 not found", thrown.getMessage());
    }

    @Test
    void testUpdateField_Success() {
        // Arrange
        Long fieldId = 1L;
        Long farmId = 1L;
        FieldDto fieldDto = new FieldDto();
        fieldDto.setId(fieldId);
        fieldDto.setFarmId(farmId);
        fieldDto.setArea(100.0);
        fieldDto.setName("Updated Field");

        Farm farm = new Farm();
        farm.setId(farmId);
        farm.setArea(200.0);  // Total farm area

        Field existingField = new Field();
        existingField.setId(fieldId);
        existingField.setName("Old Field");
        existingField.setFarm(farm);
        existingField.setArea(50.0);

        Field updatedField = new Field();
        updatedField.setId(fieldId);
        updatedField.setName("Updated Field");
        updatedField.setFarm(farm);
        updatedField.setArea(100.0);

        when(fieldRepository.findById(fieldId)).thenReturn(Optional.of(existingField));
        when(fieldMapper.toField(fieldDto)).thenReturn(updatedField);
        when(fieldRepository.save(updatedField)).thenReturn(updatedField);
        when(fieldMapper.toDto(updatedField)).thenReturn(fieldDto);

        // Act
        FieldDto result = fieldService.update(fieldId, fieldDto);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Field", result.getName());
        assertEquals(100.0, result.getArea());
    }

    @Test
    void testDeleteField_Success() {
        // Arrange
        Long fieldId = 1L;
        Field field = new Field();
        field.setId(fieldId);

        when(fieldRepository.findById(fieldId)).thenReturn(Optional.of(field));

        // Act
        fieldService.delete(fieldId);

        // Assert
        verify(fieldRepository, times(1)).delete(field);
    }

    @Test
    void testFindAllFields() {
        // Arrange
        Pageable pageable = mock(Pageable.class);

        // Mock data
        long farmId = 1L;  // Farm ID
        Farm farm = new Farm();
        farm.setId(farmId);  // Set the farm ID

        Field field = new Field(1L, "Test Field", 50.0, farm, null);  // Set the Farm object in the Field entity
        Page<Field> fieldPage = new PageImpl<>(List.of(field));

        // Mock the repository to return Page<Field>
        when(fieldRepository.findAll(pageable)).thenReturn(fieldPage);

        // Mock the mapper to convert a Field to a FieldDto
        FieldDto fieldDto = new FieldDto(1L, "Test Field", 50.0, farmId, null);  // Farm ID in DTO
        when(fieldMapper.toDto(field)).thenReturn(fieldDto);

        // Act
        Page<FieldDto> result = fieldService.findAll(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Test Field", result.getContent().get(0).getName());
    }



}
