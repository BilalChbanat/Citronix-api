package com.citronix.api.mapper;


import com.citronix.api.DTO.FieldDto;
import com.citronix.api.domains.Field;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FieldMapper {

    Field toField(FieldDto fieldDto);

    FieldDto toDto(Field field);
}
