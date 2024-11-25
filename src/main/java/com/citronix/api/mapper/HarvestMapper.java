package com.citronix.api.mapper;

import com.citronix.api.DTO.HarvestDto;
import com.citronix.api.domains.Harvest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HarvestMapper  {

    Harvest toHarvest(HarvestDto harvestDto);

    HarvestDto toDto(Harvest harvest);

}
