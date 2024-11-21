package com.citronix.api.service.interfaces;

import com.citronix.api.domains.Farm;
import com.citronix.api.DTO.FarmDto;

import java.util.List;

public interface FarmService {
    Farm create(FarmDto farmRequestDTO);

    void delete(Long id);

    Farm findById(Long id);

    Farm update(Long id, FarmDto farmDto);

    List<Farm> findAll();
}