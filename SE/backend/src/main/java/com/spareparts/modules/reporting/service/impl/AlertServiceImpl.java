package com.spareparts.modules.reporting.service.impl;

import com.spareparts.modules.inventory.entity.SparePart;
import com.spareparts.modules.inventory.repository.SparePartRepository;
import com.spareparts.modules.procurement.entity.OrderStatus;
import com.spareparts.modules.procurement.entity.PurchaseOrder;
import com.spareparts.modules.procurement.repository.PurchaseOrderRepository;
import com.spareparts.modules.reporting.dto.AlertResponse;
import com.spareparts.modules.reporting.service.AlertService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlertServiceImpl implements AlertService {

    private static final List<OrderStatus> ACTIVE_ORDER_STATUSES = List.of(OrderStatus.PENDING, OrderStatus.APPROVED);

    private final SparePartRepository sparePartRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;

    public AlertServiceImpl(SparePartRepository sparePartRepository, PurchaseOrderRepository purchaseOrderRepository) {
        this.sparePartRepository = sparePartRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    @Override
    public List<AlertResponse> getAlerts() {
        List<AlertResponse> alerts = new ArrayList<>();

        for (SparePart part : sparePartRepository.findLowStockItems()) {
            boolean outOfStock = part.getStockQuantity() <= 0;
            alerts.add(new AlertResponse(
                    "part-" + part.getId(),
                    outOfStock ? "OUT_OF_STOCK" : "LOW_STOCK",
                    outOfStock ? "CRITICAL" : "WARNING",
                    outOfStock
                            ? part.getName() + " is out of stock"
                            : part.getName() + " is running low",
                    "SKU " + part.getPartNumber() + " has " + part.getStockQuantity()
                            + " unit(s) left (reorder level " + part.getReorderLevel() + ")",
                    "/inventory"
            ));
        }

        for (PurchaseOrder po : purchaseOrderRepository.findOverdueOrders(LocalDate.now(), ACTIVE_ORDER_STATUSES)) {
            String supplierName = po.getSupplier() != null ? po.getSupplier().getName() : "unknown supplier";
            alerts.add(new AlertResponse(
                    "po-" + po.getId(),
                    "OVERDUE_PO",
                    "WARNING",
                    "PO " + po.getOrderNumber() + " is overdue",
                    "Expected " + po.getExpectedDeliveryDate() + " from " + supplierName,
                    "/purchase-orders"
            ));
        }

        return alerts;
    }
}
