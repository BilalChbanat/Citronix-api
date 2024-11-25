package com.citronix.api.controllers;


import com.citronix.api.DTO.SaleDto;
import com.citronix.api.domains.Sale;
import com.citronix.api.service.interfaces.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @PostMapping("/save")
    public ResponseEntity<SaleDto> createSale(SaleDto sale) {
        SaleDto createdSale = saleService.create(sale);
        return ResponseEntity.ok(createdSale);
    }

    @GetMapping
    public ResponseEntity<SaleDto> getAllSales(Pageable pageable) {
        SaleDto sales = (SaleDto) saleService.findAll(pageable);
        return ResponseEntity.ok(sales);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SaleDto> updateSale(@PathVariable Long id, SaleDto sale) {
        SaleDto updatedSale = saleService.update(id, sale);
        return ResponseEntity.ok(updatedSale);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable Long id) {
        saleService.delete(id);
        return ResponseEntity.noContent().build();
    }



}
