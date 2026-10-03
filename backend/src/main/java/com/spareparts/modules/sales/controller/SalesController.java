package com.spareparts.modules.sales.controller;

import com.spareparts.modules.sales.dto.SaleCreateRequest;
import com.spareparts.modules.sales.dto.SaleResponse;
import com.spareparts.modules.sales.dto.SaleUpdateRequest;
import com.spareparts.modules.sales.service.SalesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SalesController {

    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }


    private final SalesService salesService;

    @PostMapping
    public ResponseEntity<SaleResponse> createSale(@RequestBody SaleCreateRequest request) {
        return new ResponseEntity<>(salesService.createSale(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SaleResponse>> getAllSales() {
        return ResponseEntity.ok(salesService.getAllSales());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleResponse> getSaleById(@PathVariable Long id) {
        return ResponseEntity.ok(salesService.getSaleById(id));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<SaleResponse> updateStatus(@PathVariable Long id, @RequestBody SaleUpdateRequest request) {
        return ResponseEntity.ok(salesService.updateSaleStatus(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSaleRecord(@PathVariable Long id) {
        salesService.deleteSaleRecord(id);
        return ResponseEntity.noContent().build();
    }
}
