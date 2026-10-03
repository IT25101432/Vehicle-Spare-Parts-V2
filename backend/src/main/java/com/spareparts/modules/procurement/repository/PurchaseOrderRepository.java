package com.spareparts.modules.procurement.repository;

import com.spareparts.modules.procurement.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spareparts.modules.procurement.entity.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
    boolean existsByOrderNumber(String orderNumber);

    long countByStatus(OrderStatus status);

    @Query("SELECT COALESCE(SUM(p.totalAmount), 0) FROM PurchaseOrder p WHERE p.orderDate >= :start AND p.orderDate <= :end")
    BigDecimal sumTotalAmountByDateRange(
            @Param("start") LocalDate start,
            @Param("end") LocalDate end);

    @Query("SELECT p FROM PurchaseOrder p LEFT JOIN FETCH p.supplier " +
            "WHERE p.expectedDeliveryDate < :today AND p.status IN :activeStatuses " +
            "ORDER BY p.expectedDeliveryDate ASC")
    List<PurchaseOrder> findOverdueOrders(
            @Param("today") LocalDate today,
            @Param("activeStatuses") List<OrderStatus> activeStatuses);
}
