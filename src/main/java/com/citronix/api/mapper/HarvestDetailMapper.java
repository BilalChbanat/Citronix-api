package com.citronix.api.mapper;

import com.citronix.api.DTO.HarvestDetailDto;
import com.citronix.api.domains.HarvestDetail;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface HarvestDetailMapper {

    HarvestDetailDto toDto(HarvestDetail harvestDetail);

    HarvestDetail toEntity(HarvestDetailDto harvestDetailDto);

}
