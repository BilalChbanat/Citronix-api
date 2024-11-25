package com.citronix.api.service.implementations;


import com.citronix.api.DTO.SaleDto;
import com.citronix.api.domains.Sale;
import com.citronix.api.mapper.SaleMapper;
import com.citronix.api.repository.SaleRepository;
import com.citronix.api.service.interfaces.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final SaleMapper saleMapper;

    @Override
    public Page<SaleDto> findAll(Pageable pageable) {
        return saleRepository.findAll(pageable).map(saleMapper::toDto);
    }

    @Override
    public SaleDto findById(Long id) {
        return saleRepository.findById(id)
                .map(saleMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Sale with id '" + id + "' not found"));
    }

    @Override
    public SaleDto create(SaleDto saleDto) {
        return saleMapper.toDto(saleRepository.save(saleMapper.toSale(saleDto)));
    }

    @Override
    public SaleDto update(Long id, SaleDto saleDto) {
        SaleDto sale  = findById(id);

        Sale saleUpdate = saleMapper.toSale(saleDto);
        saleUpdate.setId(sale.getId());

        return saleMapper.toDto(saleRepository.save(saleUpdate));

    }

    @Override
    public void delete(Long id) {
        saleRepository.deleteById(id);
    }




}
