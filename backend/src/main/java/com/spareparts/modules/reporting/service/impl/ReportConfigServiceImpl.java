package com.spareparts.modules.reporting.service.impl;

import com.spareparts.modules.inventory.repository.SparePartRepository;
import com.spareparts.modules.procurement.repository.PurchaseOrderRepository;
import com.spareparts.modules.reporting.dto.ReportConfigCreateRequest;
import com.spareparts.modules.reporting.dto.ReportConfigUpdateRequest;
import com.spareparts.modules.reporting.dto.ReportResponse;
import com.spareparts.modules.reporting.entity.ReportConfig;
import com.spareparts.modules.reporting.entity.ReportType;
import com.spareparts.modules.reporting.repository.ReportConfigRepository;
import com.spareparts.modules.reporting.service.ReportConfigService;
import com.spareparts.modules.sales.repository.SaleRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportConfigServiceImpl implements ReportConfigService {

    public ReportConfigServiceImpl(ReportConfigRepository reportConfigRepository, SaleRepository saleRepository, PurchaseOrderRepository purchaseOrderRepository, SparePartRepository sparePartRepository) {
        this.reportConfigRepository = reportConfigRepository;
        this.saleRepository = saleRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.sparePartRepository = sparePartRepository;
    }


    private final ReportConfigRepository reportConfigRepository;
    private final SaleRepository saleRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final SparePartRepository sparePartRepository;

    @Override
    public ReportResponse createReportTemplate(ReportConfigCreateRequest request) {
        String reportId = generateReportId(request.getReportType());
        BigDecimal total = computeTotal(request.getReportType(), request.getStartDate(), request.getEndDate());

        ReportConfig config = ReportConfig.builder()
                .reportId(reportId)
                .name(request.getName())
                .reportType(request.getReportType())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .totalFinancialValue(total)
                .build();

        return mapToResponse(reportConfigRepository.save(config));
    }

    @Override
    public ReportResponse getReport(Long id) {
        return mapToResponse(reportConfigRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report Config not found")));
    }

    @Override
    public List<ReportResponse> getAllReports() {
        return reportConfigRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ReportResponse updateSchedule(Long id, ReportConfigUpdateRequest request) {
        ReportConfig config = reportConfigRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report Config not found"));

        config.setName(request.getName());

        // If end date changed, update it and recompute the financial total
        if (request.getEndDate() != null && !request.getEndDate().equals(config.getEndDate())) {
            config.setEndDate(request.getEndDate());
            config.setTotalFinancialValue(
                    computeTotal(config.getReportType(), config.getStartDate(), request.getEndDate())
            );
        }

        return mapToResponse(reportConfigRepository.save(config));
    }

    @Override
    public void deleteReport(Long id) {
        reportConfigRepository.deleteById(id);
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    /**
     * Generates a human-readable report ID based on type and a 1-based sequence.
     * Format: PREFIX-NNNN  e.g. SALES-0001, PROC-0003, INV-0002
     */
    private String generateReportId(ReportType type) {
        long count = reportConfigRepository.countByReportType(type);
        String prefix = switch (type) {
            case SALES_SUMMARY       -> "SALES";
            case PROCUREMENT_SUMMARY -> "PROC";
            case INVENTORY_SUMMARY -> "INV";
        };
        return String.format("%s-%04d", prefix, count + 1);
    }

    private BigDecimal computeTotal(ReportType type, LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return BigDecimal.ZERO;
        }
        return switch (type) {
            case SALES_SUMMARY -> {
                LocalDateTime start = startDate.atStartOfDay();
                LocalDateTime end   = endDate.atTime(LocalTime.MAX);
                yield saleRepository.sumTotalAmountByDateRange(start, end);
            }
            case PROCUREMENT_SUMMARY ->
                    purchaseOrderRepository.sumTotalAmountByDateRange(startDate, endDate);
            case INVENTORY_SUMMARY -> {
                LocalDateTime start = startDate.atStartOfDay();
                LocalDateTime end   = endDate.atTime(LocalTime.MAX);
                yield sparePartRepository.sumStockValueByCreatedAtRange(start, end);
            }
        };
    }

    private ReportResponse mapToResponse(ReportConfig config) {
        return ReportResponse.builder()
                .id(config.getId())
                .reportId(config.getReportId())
                .name(config.getName())
                .reportType(config.getReportType())
                .startDate(config.getStartDate())
                .endDate(config.getEndDate())
                .totalFinancialValue(config.getTotalFinancialValue())
                .build();
    }
}
