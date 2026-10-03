package com.spareparts.modules.compatibility.controller;

import com.spareparts.modules.compatibility.dto.CompatibilityCreateRequest;
import com.spareparts.modules.compatibility.dto.CompatibilityResponse;
import com.spareparts.modules.compatibility.service.CompatibilityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mappings")
public class CompatibilityController {

    public CompatibilityController(CompatibilityService compatibilityService) {
        this.compatibilityService = compatibilityService;
    }


    private final CompatibilityService compatibilityService;

    @PostMapping
    public ResponseEntity<CompatibilityResponse> create(@RequestBody CompatibilityCreateRequest request) {
        return new ResponseEntity<>(compatibilityService.addMapping(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CompatibilityResponse>> getAll() {
        return ResponseEntity.ok(compatibilityService.getMappingsByPart("0"));
    }

    @GetMapping("/part/{partNumber}")
    public ResponseEntity<List<CompatibilityResponse>> getByPart(@PathVariable String partNumber) {
        return ResponseEntity.ok(compatibilityService.getMappingsByPart(partNumber));
    }

    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<List<CompatibilityResponse>> getByVehicle(@PathVariable Long vehicleId) {
        return ResponseEntity.ok(compatibilityService.getMappingsByVehicle(vehicleId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        compatibilityService.deleteMapping(id);
        return ResponseEntity.noContent().build();
    }
}
