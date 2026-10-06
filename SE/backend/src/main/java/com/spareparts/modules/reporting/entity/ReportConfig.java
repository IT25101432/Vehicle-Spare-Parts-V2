package com.spareparts.modules.reporting.entity;

import com.spareparts.core.auditing.AuditableEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "report_configs")
public class ReportConfig extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "report_id")
    private String reportId;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "report_type", nullable = false)
    private ReportType reportType;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "total_financial_value", precision = 19, scale = 2)
    private BigDecimal totalFinancialValue;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getReportId() { return reportId; }
    public void setReportId(String reportId) { this.reportId = reportId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public ReportType getReportType() { return reportType; }
    public void setReportType(ReportType reportType) { this.reportType = reportType; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public BigDecimal getTotalFinancialValue() { return totalFinancialValue; }
    public void setTotalFinancialValue(BigDecimal totalFinancialValue) { this.totalFinancialValue = totalFinancialValue; }

    public static ReportConfigBuilder builder() { return new ReportConfigBuilder(); }
    public static class ReportConfigBuilder {
        private String reportId; private String name; private ReportType reportType; private LocalDate startDate; private LocalDate endDate; private BigDecimal totalFinancialValue;
        public ReportConfigBuilder reportId(String reportId) { this.reportId = reportId; return this; }
        public ReportConfigBuilder name(String name) { this.name = name; return this; }
        public ReportConfigBuilder reportType(ReportType reportType) { this.reportType = reportType; return this; }
        public ReportConfigBuilder startDate(LocalDate startDate) { this.startDate = startDate; return this; }
        public ReportConfigBuilder endDate(LocalDate endDate) { this.endDate = endDate; return this; }
        public ReportConfigBuilder totalFinancialValue(BigDecimal totalFinancialValue) { this.totalFinancialValue = totalFinancialValue; return this; }
        public ReportConfig build() {
            ReportConfig c = new ReportConfig();
            c.setReportId(reportId); c.setName(name); c.setReportType(reportType); c.setStartDate(startDate); c.setEndDate(endDate); c.setTotalFinancialValue(totalFinancialValue);
            return c;
        }
    }
}
