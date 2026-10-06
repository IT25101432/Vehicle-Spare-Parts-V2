package com.spareparts.modules.reporting.service;

import com.spareparts.modules.reporting.dto.AnalyticsSummaryDto;

public interface AnalyticsService {
    AnalyticsSummaryDto getLiveAnalytics();
}