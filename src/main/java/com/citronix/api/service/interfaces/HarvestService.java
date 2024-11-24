package com.citronix.api.service.interfaces;

import com.citronix.api.DTO.HarvestDto;
import com.citronix.api.domains.Harvest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HarvestService {

    HarvestDto create(HarvestDto harvestDto);
    HarvestDto update(Long id , HarvestDto harvestDto);
    void  delete(Long id);
    Harvest findById(Long id);
    Page<HarvestDto> findAll(Pageable pageable);
}
