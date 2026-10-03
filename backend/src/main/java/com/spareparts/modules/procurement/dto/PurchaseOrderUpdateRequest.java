package com.spareparts.modules.procurement.dto;

import com.spareparts.modules.procurement.entity.OrderStatus;
import java.time.LocalDate;

public class PurchaseOrderUpdateRequest {
    private LocalDate expectedDeliveryDate;
    private OrderStatus status;

    public LocalDate getExpectedDeliveryDate() { return expectedDeliveryDate; }
    public void setExpectedDeliveryDate(LocalDate expectedDeliveryDate) { this.expectedDeliveryDate = expectedDeliveryDate; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
}
