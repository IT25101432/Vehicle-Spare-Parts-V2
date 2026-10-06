package com.spareparts.modules.reporting.service;

import com.spareparts.modules.reporting.dto.ReportConfigCreateRequest;
import com.spareparts.modules.reporting.dto.ReportConfigUpdateRequest;
import com.spareparts.modules.reporting.dto.ReportResponse;

import java.util.List;

public interface ReportConfigService {
    ReportResponse createReportTemplate(ReportConfigCreateRequest request);
    ReportResponse getReport(Long id);
    List<ReportResponse> getAllReports();
    ReportResponse updateSchedule(Long id, ReportConfigUpdateRequest request);
    void deleteReport(Long id);
}
