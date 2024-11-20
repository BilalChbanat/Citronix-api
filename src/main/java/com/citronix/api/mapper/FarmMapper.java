package com.citronix.api.mapper;


import com.citronix.api.domains.Farm;
import com.citronix.api.DTO.FarmDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface FarmMapper {

    @Mapping(source = "createdAt", target = "createdAt")
    Farm toFarm(FarmDto farmDto);

    FarmDto toDto(Farm farm);
}
