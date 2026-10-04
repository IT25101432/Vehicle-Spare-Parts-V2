package com.spareparts.modules.inventory.repository;

import com.spareparts.modules.inventory.entity.SparePart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SparePartRepository extends JpaRepository<SparePart, Long> {
    boolean existsByPartNumber(String partNumber);
    Optional<SparePart> findByPartNumber(String partNumber);

    @Query("SELECT COUNT(s) FROM SparePart s WHERE s.stockQuantity <= s.reorderLevel")
    long countLowStockItems();

    @Query("SELECT s FROM SparePart s WHERE s.stockQuantity <= s.reorderLevel ORDER BY s.stockQuantity ASC, s.name ASC")
    List<SparePart> findLowStockItems();

    @Query("SELECT COALESCE(SUM(s.price * s.stockQuantity), 0) FROM SparePart s WHERE s.createdAt >= :start AND s.createdAt <= :end")
    BigDecimal sumStockValueByCreatedAtRange(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);
}
