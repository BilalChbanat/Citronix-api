package com.citronix.api.service.implementations;

import com.citronix.api.DTO.TreeDto;
import com.citronix.api.domains.Field;
import com.citronix.api.domains.Tree;
import com.citronix.api.enums.TreeStatus;
import com.citronix.api.exception.EntityNotFoundException;
import com.citronix.api.mapper.TreeMapper;
import com.citronix.api.repository.FieldRepository;
import com.citronix.api.repository.TreeRepository;
import com.citronix.api.service.interfaces.TreeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
public class TreeServiceImpl implements TreeService {

    private final TreeRepository treeRepository;
    private final TreeMapper treeMapper;
    private final FieldRepository fieldRepository;


    @Override
    public TreeDto create(TreeDto treeDto) {
        // Fetch the field and ensure it exists
        Field field = fieldRepository.findById(treeDto.getFieldId())
                .orElseThrow(() -> new EntityNotFoundException("Field not found with id: " + treeDto.getFieldId()));

        // Ensure plantation happens only in March, April, or May
        LocalDate plantationDate = LocalDate.from(treeDto.getPlantationDate());
        int plantingMonth = plantationDate.getMonthValue();
        if (plantingMonth < Tree.PLANTING_START_MONTH || plantingMonth > Tree.PLANTING_END_MONTH) {
            throw new IllegalArgumentException("Invalid plantation date. Trees can only be planted between March and May.");
        }

        // Ensure the tree is not older than 20 years
        int treeAge = Period.between(plantationDate, LocalDate.now()).getYears();
        if (treeAge > Tree.MAX_TREE_AGE) {
            throw new IllegalArgumentException("Tree age cannot exceed " + Tree.MAX_TREE_AGE + " years.");
        }

        // Check that field density does not exceed 100 trees per 1000 m²
        long treeCountInField = treeRepository.countByFieldId(field.getId());
        double maxTreesAllowed = field.getArea() / 10.0; // 100 trees per 1000 m²
        if (treeCountInField >= maxTreesAllowed) {
            throw new IllegalArgumentException("Field capacity exceeded. Maximum allowed is " + (int) maxTreesAllowed + " trees.");
        }

        // Map DTO to entity, assign field, and save tree
        Tree tree = treeMapper.toTree(treeDto);
        tree.setField(field);
        tree = treeRepository.save(tree);

        // Return the saved entity as DTO
        return treeMapper.toDto(tree);
    }



    @Override
    public void delete(Long id) {
        Tree tree =findById(id);
        treeRepository.delete(tree);

    }

    @Override
    public Tree findById(Long id) {
        return treeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tree not found with id: " + id));
    }

    @Override
    public TreeDto update(Long id, TreeDto treeDto) {
        Tree existingTree = findById(id);

        if (treeDto.getFieldId() != null) {
            Field field = fieldRepository.findById(treeDto.getFieldId())
                    .orElseThrow(() -> new EntityNotFoundException("Field not found"));
            existingTree.setField(field);
        }

        if (treeDto.getPlantationDate() != null) {
            existingTree.setPlantationDate(treeDto.getPlantationDate());
        }

        int age = existingTree.getAge();
        if (age < Tree.YOUNG_TREE_AGE_LIMIT) {
            existingTree.setStatus(TreeStatus.YOUNG);
        } else if (age <= Tree.MATURE_TREE_AGE_LIMIT) {
            existingTree.setStatus(TreeStatus.PRODUCTIVE);
        } else {
            existingTree.setStatus(TreeStatus.NON_PRODUCTIVE);
        }

        Tree updatedTree = treeRepository.save(existingTree);
        return treeMapper.toDto(updatedTree);
    }


    @Override
    public Page<TreeDto> findAll(Pageable pageable) {
        return treeRepository.findAll(pageable)
                .map(treeMapper::toDto);
    }
}
