package com.citronix.api.service.implementations;

import com.citronix.api.DTO.HarvestDto;
import com.citronix.api.domains.Field;
import com.citronix.api.domains.Harvest;
import com.citronix.api.domains.HarvestDetail;
import com.citronix.api.domains.Tree;
import com.citronix.api.enums.SeasonType;
import com.citronix.api.exception.EntityNotFoundException;
import com.citronix.api.mapper.HarvestMapper;
import com.citronix.api.repository.FieldRepository;
import com.citronix.api.repository.HarvestDetailRepository;
import com.citronix.api.repository.HarvestRepository;
import com.citronix.api.repository.TreeRepository;
import com.citronix.api.service.interfaces.HarvestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HarvestServiceImpl implements HarvestService {

    private final HarvestRepository harvestRepository;
    private final HarvestMapper harvestMapper;
    private final FieldRepository fieldRepository;
    private final TreeRepository treeRepository;
    private final HarvestDetailRepository harvestDetailRepository;

    @Override
    public HarvestDto create(HarvestDto harvestDto) {
        Harvest harvest = harvestMapper.toHarvest(harvestDto);

        if (harvestDto.getSeason() != null) {
            harvest.setSeason(SeasonType.valueOf(harvestDto.getSeason().toUpperCase()));
        }

        if (harvestDto.getHarvestDate() != null) {
            harvest.setHarvestDate(LocalDate.parse(harvestDto.getHarvestDate()));
        }

        List<Tree> trees = treeRepository.findAll();

        double totalQuantity = 0.0;
        List<HarvestDetail> harvestDetails = new ArrayList<>();
        for (Tree tree : trees) {
            double productivity = tree.getProductivity();
            totalQuantity += productivity;

            HarvestDetail detail = new HarvestDetail();
            detail.setTree(tree);
            detail.setQuantity(productivity);
            detail.setHarvest(harvest);
            harvestDetails.add(detail);
        }

        harvest = harvestRepository.save(harvest);

        if (!harvestDetails.isEmpty()) {
            harvestDetailRepository.saveAll(harvestDetails);
        }

        HarvestDto responseDto = harvestMapper.toDto(harvest);
        responseDto.setTotalQuantity(totalQuantity);

        return responseDto;
    }



    @Override
    public HarvestDto update(Long id, HarvestDto harvestDto) {
        Harvest existingHarvest = findById(id);

        if (harvestDto.getSeason() != null) {
            existingHarvest.setSeason(SeasonType.valueOf(harvestDto.getSeason().toUpperCase()));
        }

        if (harvestDto.getHarvestDate() != null) {
            existingHarvest.setHarvestDate(LocalDate.parse(harvestDto.getHarvestDate()));
        }

        if (harvestDto.getAmount() > 0) {
            existingHarvest.setAmount(harvestDto.getAmount());
        }

        if (harvestDto.getTotalQuantity() > 0) {
            existingHarvest.setTotalQuantity(harvestDto.getTotalQuantity());
        }

        Harvest updatedHarvest = harvestRepository.save(existingHarvest);
        return harvestMapper.toDto(updatedHarvest);
    }

    @Override
    public void delete(Long id) {
        Harvest harvest = findById(id);
        harvestRepository.delete(harvest);
    }

    @Override
    public Harvest findById(Long id) {
        return harvestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Harvest not found with id: " + id));
    }

    @Override
    public Page<HarvestDto> findAll(Pageable pageable) {
        return harvestRepository.findAll(pageable)
                .map(harvestMapper::toDto);
    }
}