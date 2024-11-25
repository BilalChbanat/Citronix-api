package com.citronix.api.service.interfaces;

import com.citronix.api.DTO.SaleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SaleService {

    Page<SaleDto> findAll(Pageable pageable);

    SaleDto findById(Long id);

    SaleDto create(SaleDto saleDto);

    SaleDto update(Long id, SaleDto saleDto);

    void delete(Long id);

}
