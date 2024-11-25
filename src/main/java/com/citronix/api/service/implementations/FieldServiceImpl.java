package com.citronix.api.service.implementations;

import com.citronix.api.DTO.FieldDto;
import com.citronix.api.domains.Farm;
import com.citronix.api.domains.Field;
import com.citronix.api.exception.EntityNotFoundException;
import com.citronix.api.mapper.FieldMapper;
import com.citronix.api.repository.FarmRepository;
import com.citronix.api.repository.FieldRepository;
import com.citronix.api.service.interfaces.FieldService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class FieldServiceImpl implements FieldService {

    private final FieldRepository fieldRepository;
    private final FieldMapper fieldMapper;
    private final FarmRepository farmRepository;

    @Override
    public FieldDto create(FieldDto fieldRequestDTO) {
        // Fetch the farm and ensure it exists
        Farm farm = farmRepository.findById(fieldRequestDTO.getFarmId())
                .orElseThrow(() -> new EntityNotFoundException("Farm with ID " + fieldRequestDTO.getFarmId() + " not found"));

        // Calculate the total area of existing fields in the farm
        double totalFieldsArea = fieldRepository.findTotalAreaByFarmId(farm.getId());
        double remainingArea = farm.getArea() - totalFieldsArea;

        // Check if the field's area exceeds the farm's remaining area
        if (fieldRequestDTO.getArea() > remainingArea) {
            throw new IllegalArgumentException("The field's area exceeds the farm's remaining area. Available area: " + remainingArea);
        }

        // Check if the field's area exceeds 50% of the farm's total area
        double maxAllowedFieldArea = farm.getArea() * 0.5;
        if (fieldRequestDTO.getArea() > maxAllowedFieldArea) {
            throw new IllegalArgumentException("The field's area exceeds 50% of the farm's total area. Maximum allowed: " + maxAllowedFieldArea);
        }

        // Check if the farm already has 10 fields
        long fieldCountInFarm = fieldRepository.countByFarmId(farm.getId());
        if (fieldCountInFarm >= 10) {
            throw new IllegalArgumentException("The farm already has the maximum allowed number of fields (10).");
        }

        // Map DTO to entity, assign the farm, and save the field
        Field field = fieldMapper.toField(fieldRequestDTO);
        field.setFarm(farm);
        return fieldMapper.toDto(fieldRepository.save(field));
    }


    @Override
    public void delete(Long id) {
        Field field = fieldRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Field with ID " + id + " not found"));
        fieldRepository.delete(field);
    }

    @Override
    public Field findById(Long id) {
        return fieldRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Field with ID " + id + " not found"));
    }

    @Override
    public FieldDto update(Long id, FieldDto fieldDto) {
        Field existingField = findById(id);

        Field fieldUpdate = fieldMapper.toField(fieldDto);
        fieldUpdate.setId(existingField.getId());
        fieldUpdate.setFarm(existingField.getFarm());

        Field updatedField = fieldRepository.save(fieldUpdate);
        return fieldMapper.toDto(updatedField);
    }

    @Override
    public Page<FieldDto> findAll(Pageable pageable) {
        return fieldRepository.findAll(pageable)
                .map(fieldMapper::toDto);
    }

}
