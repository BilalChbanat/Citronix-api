package com.citronix.api.mapper;

import com.citronix.api.DTO.SaleDto;
import com.citronix.api.domains.Sale;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SaleMapper {

    Sale toSale(SaleDto saleDto);

    SaleDto toDto(Sale sale);
}
