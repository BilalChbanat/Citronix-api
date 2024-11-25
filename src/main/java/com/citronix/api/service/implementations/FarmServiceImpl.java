package com.citronix.api.service.implementations;

import com.citronix.api.DTO.TreeDto;
import com.citronix.api.domains.Farm;
import com.citronix.api.repository.FarmRepository;
import com.citronix.api.service.interfaces.FarmService;
import com.citronix.api.DTO.FarmDto;
import com.citronix.api.exception.EntityAlreadyExistsException;
import com.citronix.api.exception.EntityNotFoundException;
import com.citronix.api.mapper.FarmMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FarmServiceImpl implements FarmService {

    private final FarmRepository farmRepository;
    private final FarmMapper farmMapper;

    public FarmServiceImpl(FarmRepository farmRepository,
                           FarmMapper farmMapper) {
        this.farmRepository = farmRepository;
        this.farmMapper = farmMapper;
    }

    @Override
    public Farm create(FarmDto farmRequestDTO) {
        if (farmRepository.existsByName(farmRequestDTO.getName())) {
            throw new EntityAlreadyExistsException("Farm with the name '" + farmRequestDTO.getName() + "' already exists.");
        }
        return farmRepository.save(farmMapper.toFarm(farmRequestDTO));
    }

    @Override
    public void delete(Long id) {
        Farm farm = farmRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Farm with id '" + id + "' not found"));
        farmRepository.delete(farm);
    }

    @Override
    public Farm findById(Long id) {
        return farmRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Farm with id '" + id + "' not found"));
    }

    @Override
    public Farm update(Long id, FarmDto farmDto) {
        Farm farm = findById(id);

        Farm farmupdate = farmMapper.toFarm(farmDto);
        farmupdate.setId(farm.getId());

        return farmRepository.save(farmupdate);
    }

    @Override
    public Page<FarmDto> findAll(Pageable pageable) {
        return farmRepository.findAll(pageable).map(farmMapper::toDto);
    }


}
