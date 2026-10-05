package com.spareparts.modules.procurement.dto;

import com.spareparts.modules.procurement.entity.OrderStatus;
import java.time.LocalDate;

// No @FutureOrPresent on the date here on purpose: the UI may send back the existing
// (already past) date when marking an overdue order as RECEIVED, and that must not fail.
// An invalid status string (e.g. "DONE") is rejected automatically by Jackson.
public class PurchaseOrderUpdateRequest {
    private LocalDate expectedDeliveryDate;
    private OrderStatus status;

    public LocalDate getExpectedDeliveryDate() { return expectedDeliveryDate; }
    public void setExpectedDeliveryDate(LocalDate expectedDeliveryDate) { this.expectedDeliveryDate = expectedDeliveryDate; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
}
