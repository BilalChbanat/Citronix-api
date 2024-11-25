package com.citronix.api.controllers;

import com.citronix.api.DTO.TreeDto;
import com.citronix.api.service.interfaces.TreeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trees")
public class TreeController {

    private final TreeService treeService;

    @PostMapping("/save")
    public ResponseEntity<TreeDto> createTree(@Valid @RequestBody TreeDto treeDto) {
        TreeDto createdTree = treeService.create(treeDto);
        return ResponseEntity.ok(createdTree);
    }

    @GetMapping
    public ResponseEntity<Page<TreeDto>> getAllTrees(Pageable pageable) {
        Page<TreeDto> trees = treeService.findAll(pageable);
        return ResponseEntity.ok(trees);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<TreeDto> updateTree(@PathVariable Long id,@Valid @RequestBody TreeDto treeDto) {
        TreeDto updatedTree = treeService.update(id, treeDto);
        return ResponseEntity.ok(updatedTree);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTree( @PathVariable Long id) {
        treeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
