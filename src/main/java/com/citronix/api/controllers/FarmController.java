package com.citronix.api.controllers;

import com.citronix.api.domains.Farm;
import com.citronix.api.service.interfaces.FarmService;
import com.citronix.api.DTO.FarmDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/farms")
@RequiredArgsConstructor
public class FarmController {

    private final FarmService farmService;

    @PostMapping("/save")
    public ResponseEntity<Farm> createFarm(@Valid @RequestBody FarmDto farmDto) {
        Farm createdFarm = farmService.create(farmDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFarm);
    }

    @GetMapping
    public ResponseEntity<List<Farm>> getAllFarms() {
        List<Farm> farms = farmService.findAll();
        return ResponseEntity.ok(farms);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Farm> updateFarm(@PathVariable Long id, @Valid @RequestBody FarmDto farmDto) {
        Farm updatedFarm = farmService.update(id, farmDto);
        return ResponseEntity.ok(updatedFarm);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFarm(@PathVariable Long id) {
        farmService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
