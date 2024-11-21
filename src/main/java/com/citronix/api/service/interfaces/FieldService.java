package com.citronix.api.service.interfaces;

import com.citronix.api.DTO.FieldDto;
import com.citronix.api.domains.Field;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface FieldService {
    FieldDto create(FieldDto fieldRequestDTO);

    void delete(Long id);

    Field findById(Long id);

    FieldDto update(Long id, FieldDto fieldDto);

    Page<FieldDto> findAll(Pageable pageable);

}
