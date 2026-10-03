package com.spareparts.modules.reporting.controller;

import com.spareparts.modules.reporting.dto.ReportConfigCreateRequest;
import com.spareparts.modules.reporting.dto.ReportConfigUpdateRequest;
import com.spareparts.modules.reporting.dto.ReportResponse;
import com.spareparts.modules.reporting.service.ReportConfigService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportConfigController {

    public ReportConfigController(ReportConfigService reportConfigService) {
        this.reportConfigService = reportConfigService;
    }


    private final ReportConfigService reportConfigService;

    @PostMapping
    public ResponseEntity<ReportResponse> createReportTemplate(@RequestBody ReportConfigCreateRequest request) {
        return new ResponseEntity<>(reportConfigService.createReportTemplate(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ReportResponse>> getAllReports() {
        return ResponseEntity.ok(reportConfigService.getAllReports());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportResponse> getReport(@PathVariable Long id) {
        return ResponseEntity.ok(reportConfigService.getReport(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReportResponse> updateSchedule(@PathVariable Long id, @RequestBody ReportConfigUpdateRequest request) {
        return ResponseEntity.ok(reportConfigService.updateSchedule(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        reportConfigService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }
}
