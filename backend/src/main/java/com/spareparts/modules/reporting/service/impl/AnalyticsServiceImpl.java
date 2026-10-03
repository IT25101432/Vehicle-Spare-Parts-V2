package com.spareparts.modules.reporting.service.impl;

import com.spareparts.modules.inventory.repository.SparePartRepository;
import com.spareparts.modules.procurement.entity.OrderStatus;
import com.spareparts.modules.procurement.repository.PurchaseOrderRepository;
import com.spareparts.modules.reporting.dto.AnalyticsSummaryDto;
import com.spareparts.modules.reporting.service.AnalyticsService;
import org.springframework.stereotype.Service;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {

    public AnalyticsServiceImpl(SparePartRepository sparePartRepository, PurchaseOrderRepository purchaseOrderRepository) {
        this.sparePartRepository = sparePartRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
    }


    private final SparePartRepository sparePartRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;

    @Override
    public AnalyticsSummaryDto getLiveAnalytics() {
        return AnalyticsSummaryDto.builder()
                .lowStockItemsCount((int) sparePartRepository.countLowStockItems())
                .pendingOrdersCount((int) purchaseOrderRepository.countByStatus(OrderStatus.PENDING))
                .build();
    }
}