package com.spareparts.modules.procurement.controller;

import com.spareparts.modules.procurement.dto.PurchaseOrderCreateRequest;
import com.spareparts.modules.procurement.dto.PurchaseOrderResponse;
import com.spareparts.modules.procurement.dto.PurchaseOrderUpdateRequest;
import com.spareparts.modules.procurement.service.ProcurementService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/po")
public class PurchaseOrderController {

    public PurchaseOrderController(ProcurementService procurementService) {
        this.procurementService = procurementService;
    }

    private final ProcurementService procurementService;

    @PostMapping
    public ResponseEntity<PurchaseOrderResponse> create(@Valid @RequestBody PurchaseOrderCreateRequest request) {
        return new ResponseEntity<>(procurementService.createPO(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PurchaseOrderResponse>> getAll() {
        return ResponseEntity.ok(procurementService.getAllPOs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrderResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(procurementService.getPOById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PurchaseOrderResponse> updateStatus(@PathVariable Long id, @Valid @RequestBody PurchaseOrderUpdateRequest request) {
        return ResponseEntity.ok(procurementService.updatePOStatus(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        procurementService.deletePO(id);
        return ResponseEntity.noContent().build();
    }
}
