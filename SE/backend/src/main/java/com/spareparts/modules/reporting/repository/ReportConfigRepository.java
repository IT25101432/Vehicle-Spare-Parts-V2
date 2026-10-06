package com.spareparts.modules.reporting.repository;

import com.spareparts.modules.reporting.entity.ReportConfig;
import com.spareparts.modules.reporting.entity.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportConfigRepository extends JpaRepository<ReportConfig, Long> {
    long countByReportType(ReportType reportType);
}