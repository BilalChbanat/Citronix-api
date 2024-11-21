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
        Farm farm = farmRepository.findById(fieldRequestDTO.getFarmId())
                .orElseThrow(() -> new EntityNotFoundException("Farm with ID " + fieldRequestDTO.getFarmId() + " not found"));

        double totalFieldsArea = fieldRepository.findTotalAreaByFarmId(farm.getId());

        double remainingArea = farm.getArea() - totalFieldsArea;
        if (fieldRequestDTO.getArea() > remainingArea) {
            throw new IllegalArgumentException("The field's area exceeds the farm's remaining area. Available area: " + remainingArea);
        }

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
