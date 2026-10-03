package com.spareparts.modules.reporting.controller;

import com.spareparts.modules.reporting.dto.AnalyticsSummaryDto;
import com.spareparts.modules.reporting.service.AnalyticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }


    private final AnalyticsService analyticsService;

    @GetMapping
    public ResponseEntity<AnalyticsSummaryDto> getLiveAnalytics() {
        return ResponseEntity.ok(analyticsService.getLiveAnalytics());
    }
}