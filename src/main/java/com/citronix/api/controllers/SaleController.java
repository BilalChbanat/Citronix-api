package com.citronix.api.controllers;


import com.citronix.api.DTO.SaleDto;
import com.citronix.api.domains.Sale;
import com.citronix.api.service.interfaces.SaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @PostMapping("/save")
    public ResponseEntity<SaleDto> createSale(@Valid @RequestBody SaleDto saleDto) {
        if (saleDto.getHarvestId() == null) {
            throw new IllegalArgumentException("Harvest ID cannot be null");
        }
        SaleDto createdSale = saleService.create(saleDto);
        return ResponseEntity.ok(createdSale);
    }


    @GetMapping
    public ResponseEntity<Page<SaleDto>> getAllSales(Pageable pageable) {
        Page<SaleDto> sales = saleService.findAll(pageable);
        return ResponseEntity.ok(sales);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SaleDto> updateSale(@PathVariable Long id, @Valid @RequestBody SaleDto sale) {
        SaleDto updatedSale = saleService.update(id, sale);
        return ResponseEntity.ok(updatedSale);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable Long id) {
        saleService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
