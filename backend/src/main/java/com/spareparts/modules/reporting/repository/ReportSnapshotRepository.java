package com.spareparts.modules.reporting.repository;

import com.spareparts.modules.reporting.entity.ReportSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportSnapshotRepository extends JpaRepository<ReportSnapshot, Long> {

}