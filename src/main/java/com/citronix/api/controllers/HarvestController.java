package com.citronix.api.controllers;


import com.citronix.api.DTO.HarvestDto;
import com.citronix.api.domains.Harvest;
import com.citronix.api.service.interfaces.HarvestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/harvests")
@RequiredArgsConstructor
public class HarvestController {

    private final HarvestService harvestService;

    @PostMapping("/save")
    public ResponseEntity<HarvestDto> createHarvest(@Valid @RequestBody HarvestDto harvestDto) {
        HarvestDto createdHarvest = harvestService.create(harvestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHarvest);
    }

    @GetMapping
    public ResponseEntity<Page<HarvestDto>> getAllHarvests(Pageable pageable) {
        Page<HarvestDto> harvests = harvestService.findAll(pageable);
        return ResponseEntity.ok(harvests);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HarvestDto> updateHarvest(@PathVariable Long id, @Valid @RequestBody HarvestDto harvestDto) {
        HarvestDto updatedHarvest = harvestService.update(id, harvestDto);
        return ResponseEntity.ok(updatedHarvest);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteHarvest(@Valid @PathVariable Long id) {
        harvestService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
