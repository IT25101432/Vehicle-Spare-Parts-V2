package com.spareparts.modules.inventory.controller;

import com.spareparts.modules.inventory.dto.SparePartCreateRequest;
import com.spareparts.modules.inventory.dto.SparePartResponse;
import com.spareparts.modules.inventory.dto.SparePartUpdateRequest;
import com.spareparts.modules.inventory.service.SparePartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parts")
public class SparePartController {

    public SparePartController(SparePartService sparePartService) {
        this.sparePartService = sparePartService;
    }


    private final SparePartService sparePartService;

    @PostMapping
    public ResponseEntity<SparePartResponse> create(@RequestBody SparePartCreateRequest request) {
        return new ResponseEntity<>(sparePartService.addPart(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SparePartResponse>> getAll() {
        return ResponseEntity.ok(sparePartService.searchParts(""));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SparePartResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(sparePartService.getPartById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SparePartResponse> update(@PathVariable Long id, @RequestBody SparePartUpdateRequest request) {
        return ResponseEntity.ok(sparePartService.updatePart(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        sparePartService.deletePart(id);
        return ResponseEntity.noContent().build();
    }
}
