package com.spareparts.modules.reporting.dto;

import com.spareparts.modules.reporting.entity.ReportType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ReportResponse {
    private Long id;
    private String reportId;
    private String name;
    private ReportType reportType;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal totalFinancialValue;

    public ReportResponse() {}
    public ReportResponse(Long id, String reportId, String name, ReportType reportType, LocalDate startDate, LocalDate endDate, BigDecimal totalFinancialValue) {
        this.id = id; this.reportId = reportId; this.name = name; this.reportType = reportType; this.startDate = startDate; this.endDate = endDate; this.totalFinancialValue = totalFinancialValue;
    }
    
    public Long getId() { return id; }
    public String getReportId() { return reportId; }
    public String getName() { return name; }
    public ReportType getReportType() { return reportType; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public BigDecimal getTotalFinancialValue() { return totalFinancialValue; }

    public static ReportResponseBuilder builder() { return new ReportResponseBuilder(); }
    public static class ReportResponseBuilder {
        private Long id; private String reportId; private String name; private ReportType reportType; private LocalDate startDate; private LocalDate endDate; private BigDecimal totalFinancialValue;
        public ReportResponseBuilder id(Long id) { this.id = id; return this; }
        public ReportResponseBuilder reportId(String reportId) { this.reportId = reportId; return this; }
        public ReportResponseBuilder name(String name) { this.name = name; return this; }
        public ReportResponseBuilder reportType(ReportType reportType) { this.reportType = reportType; return this; }
        public ReportResponseBuilder startDate(LocalDate startDate) { this.startDate = startDate; return this; }
        public ReportResponseBuilder endDate(LocalDate endDate) { this.endDate = endDate; return this; }
        public ReportResponseBuilder totalFinancialValue(BigDecimal totalFinancialValue) { this.totalFinancialValue = totalFinancialValue; return this; }
        public ReportResponse build() { return new ReportResponse(id, reportId, name, reportType, startDate, endDate, totalFinancialValue); }
    }
}
