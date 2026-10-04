package com.spareparts.modules.compatibility.controller;

import com.spareparts.modules.compatibility.dto.VehicleCreateRequest;
import com.spareparts.modules.compatibility.dto.VehicleResponse;
import com.spareparts.modules.compatibility.dto.VehicleUpdateRequest;
import com.spareparts.modules.compatibility.service.CompatibilityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    public VehicleController(CompatibilityService compatibilityService) {
        this.compatibilityService = compatibilityService;
    }


    private final CompatibilityService compatibilityService;

    @PostMapping
    public ResponseEntity<VehicleResponse> create(@RequestBody VehicleCreateRequest request) {
        return new ResponseEntity<>(compatibilityService.createVehicle(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<VehicleResponse>> getAll() {
        return ResponseEntity.ok(compatibilityService.getAllVehicles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(compatibilityService.getVehicleById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponse> update(@PathVariable Long id, @RequestBody VehicleUpdateRequest request) {
        return ResponseEntity.ok(compatibilityService.updateVehicle(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        compatibilityService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }
}
