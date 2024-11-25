package com.citronix.api.service.implementations;


import com.citronix.api.DTO.SaleDto;
import com.citronix.api.domains.Harvest;
import com.citronix.api.domains.Sale;
import com.citronix.api.mapper.SaleMapper;
import com.citronix.api.repository.HarvestRepository;
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
    private final HarvestRepository harvestRepository;

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
        if (saleDto.getHarvestId() == null) {
            throw new IllegalArgumentException("Harvest ID cannot be null");
        }

        Harvest harvest = harvestRepository.findById(saleDto.getHarvestId())
                .orElseThrow(() -> new IllegalArgumentException("Harvest not found for the given ID: " + saleDto.getHarvestId()));

        if (saleDto.getQuantity() > harvest.getTotalQuantity()) {
            throw new IllegalArgumentException("Sale quantity exceeds the available harvest quantity. Available: " + harvest.getTotalQuantity());
        }

        double updatedQuantity = harvest.getTotalQuantity() - saleDto.getQuantity();
        harvest.setTotalQuantity(updatedQuantity);

        harvestRepository.save(harvest);

        Sale sale = saleMapper.toSale(saleDto);
        sale.setHarvest(harvest);
        Sale savedSale = saleRepository.save(sale);

        return saleMapper.toDto(savedSale);
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
