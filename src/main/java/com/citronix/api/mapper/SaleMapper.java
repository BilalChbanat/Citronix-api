package com.citronix.api.mapper;

import com.citronix.api.DTO.SaleDto;
import com.citronix.api.domains.Sale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SaleMapper {

    @Mapping(source = "harvestId", target = "harvest.id")
    Sale toSale(SaleDto saleDto);


    @Mapping(source = "harvest.id", target = "harvestId")
    SaleDto toDto(Sale sale);
}
