package com.spareparts.modules.procurement.dto;

import java.time.LocalDate;
import java.util.List;
import java.math.BigDecimal;

public class PurchaseOrderCreateRequest {
    private Long supplierId;
    private LocalDate expectedDeliveryDate;
    private List<PurchaseOrderItemDto> items;

    public Long getSupplierId() { return supplierId; }
    public void setSupplierId(Long supplierId) { this.supplierId = supplierId; }
    public LocalDate getExpectedDeliveryDate() { return expectedDeliveryDate; }
    public void setExpectedDeliveryDate(LocalDate expectedDeliveryDate) { this.expectedDeliveryDate = expectedDeliveryDate; }
    public List<PurchaseOrderItemDto> getItems() { return items; }
    public void setItems(List<PurchaseOrderItemDto> items) { this.items = items; }

    public static class PurchaseOrderItemDto {
        private Long sparePartId;
        private Integer quantity;
        private BigDecimal unitPrice;

        public Long getSparePartId() { return sparePartId; }
        public void setSparePartId(Long sparePartId) { this.sparePartId = sparePartId; }
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
        public BigDecimal getUnitPrice() { return unitPrice; }
        public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    }
}
