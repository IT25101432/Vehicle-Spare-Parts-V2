package com.spareparts.modules.procurement.dto;

import com.spareparts.modules.procurement.entity.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class PurchaseOrderResponse {
    private Long id;
    private String orderNumber;
    private Long supplierId;
    private String supplierName;
    private LocalDate orderDate;
    private LocalDate expectedDeliveryDate;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private List<PurchaseOrderItemResponse> items;

    public PurchaseOrderResponse() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }
    public Long getSupplierId() { return supplierId; }
    public void setSupplierId(Long supplierId) { this.supplierId = supplierId; }
    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }
    public LocalDate getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate; }
    public LocalDate getExpectedDeliveryDate() { return expectedDeliveryDate; }
    public void setExpectedDeliveryDate(LocalDate expectedDeliveryDate) { this.expectedDeliveryDate = expectedDeliveryDate; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public List<PurchaseOrderItemResponse> getItems() { return items; }
    public void setItems(List<PurchaseOrderItemResponse> items) { this.items = items; }

    public static PurchaseOrderResponseBuilder builder() { return new PurchaseOrderResponseBuilder(); }

    public static class PurchaseOrderResponseBuilder {
        private Long id, supplierId;
        private String orderNumber, supplierName;
        private LocalDate orderDate, expectedDeliveryDate;
        private OrderStatus status;
        private BigDecimal totalAmount;
        private List<PurchaseOrderItemResponse> items;

        public PurchaseOrderResponseBuilder id(Long v) { this.id = v; return this; }
        public PurchaseOrderResponseBuilder orderNumber(String v) { this.orderNumber = v; return this; }
        public PurchaseOrderResponseBuilder supplierId(Long v) { this.supplierId = v; return this; }
        public PurchaseOrderResponseBuilder supplierName(String v) { this.supplierName = v; return this; }
        public PurchaseOrderResponseBuilder orderDate(LocalDate v) { this.orderDate = v; return this; }
        public PurchaseOrderResponseBuilder expectedDeliveryDate(LocalDate v) { this.expectedDeliveryDate = v; return this; }
        public PurchaseOrderResponseBuilder status(OrderStatus v) { this.status = v; return this; }
        public PurchaseOrderResponseBuilder totalAmount(BigDecimal v) { this.totalAmount = v; return this; }
        public PurchaseOrderResponseBuilder items(List<PurchaseOrderItemResponse> v) { this.items = v; return this; }
        public PurchaseOrderResponse build() {
            PurchaseOrderResponse r = new PurchaseOrderResponse();
            r.id = id; r.orderNumber = orderNumber; r.supplierId = supplierId; r.supplierName = supplierName;
            r.orderDate = orderDate; r.expectedDeliveryDate = expectedDeliveryDate;
            r.status = status; r.totalAmount = totalAmount; r.items = items;
            return r;
        }
    }

    public static class PurchaseOrderItemResponse {
        private Long id, sparePartId;
        private Integer quantity;
        private BigDecimal unitPrice, totalPrice;

        public PurchaseOrderItemResponse() {}
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public Long getSparePartId() { return sparePartId; }
        public void setSparePartId(Long sparePartId) { this.sparePartId = sparePartId; }
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
        public BigDecimal getUnitPrice() { return unitPrice; }
        public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
        public BigDecimal getTotalPrice() { return totalPrice; }
        public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }

        public static PurchaseOrderItemResponseBuilder builder() { return new PurchaseOrderItemResponseBuilder(); }

        public static class PurchaseOrderItemResponseBuilder {
            private Long id, sparePartId;
            private Integer quantity;
            private BigDecimal unitPrice, totalPrice;
            public PurchaseOrderItemResponseBuilder id(Long v) { this.id = v; return this; }
            public PurchaseOrderItemResponseBuilder sparePartId(Long v) { this.sparePartId = v; return this; }
            public PurchaseOrderItemResponseBuilder quantity(Integer v) { this.quantity = v; return this; }
            public PurchaseOrderItemResponseBuilder unitPrice(BigDecimal v) { this.unitPrice = v; return this; }
            public PurchaseOrderItemResponseBuilder totalPrice(BigDecimal v) { this.totalPrice = v; return this; }
            public PurchaseOrderItemResponse build() {
                PurchaseOrderItemResponse r = new PurchaseOrderItemResponse();
                r.id = id; r.sparePartId = sparePartId; r.quantity = quantity;
                r.unitPrice = unitPrice; r.totalPrice = totalPrice;
                return r;
            }
        }
    }
}
