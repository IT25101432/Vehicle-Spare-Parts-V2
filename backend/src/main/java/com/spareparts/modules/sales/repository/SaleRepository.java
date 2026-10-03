package com.spareparts.modules.sales.repository;

import com.spareparts.modules.sales.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT COALESCE(SUM(s.totalAmount), 0) FROM Sale s WHERE s.saleDate >= :start AND s.saleDate <= :end")
    BigDecimal sumTotalAmountByDateRange(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);
}
