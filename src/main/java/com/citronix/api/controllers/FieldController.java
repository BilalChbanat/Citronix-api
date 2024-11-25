package com.citronix.api.controllers;


import com.citronix.api.DTO.FieldDto;
import com.citronix.api.service.interfaces.FieldService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fields")
@RequiredArgsConstructor
public class FieldController {

    private final FieldService fieldService;

    @PostMapping("/save")
    public ResponseEntity<FieldDto> createField(@Valid @RequestBody FieldDto fieldDto) {
        FieldDto createdField = fieldService.create(fieldDto);
        return ResponseEntity.ok(createdField);
    }

    @GetMapping
    public ResponseEntity<Page<FieldDto>> getAllFields(Pageable pageable) {
        Page<FieldDto> fields = fieldService.findAll(pageable);
        return ResponseEntity.ok(fields);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<FieldDto> updateField( @PathVariable Long id,@Valid @RequestBody FieldDto fieldDto) {
        FieldDto updatedField = fieldService.update(id, fieldDto);
        return ResponseEntity.ok(updatedField);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteField(@Valid @PathVariable Long id) {
        fieldService.delete(id);
        return ResponseEntity.noContent().build();
    }
}