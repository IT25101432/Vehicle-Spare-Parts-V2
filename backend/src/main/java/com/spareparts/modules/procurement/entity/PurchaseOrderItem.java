package com.spareparts.modules.procurement.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "purchase_order_items")
public class PurchaseOrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_id", nullable = false)
    private PurchaseOrder purchaseOrder;

    @Column(name = "spare_part_id", nullable = false)
    private Long sparePartId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    public PurchaseOrderItem() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public PurchaseOrder getPurchaseOrder() { return purchaseOrder; }
    public void setPurchaseOrder(PurchaseOrder purchaseOrder) { this.purchaseOrder = purchaseOrder; }
    public Long getSparePartId() { return sparePartId; }
    public void setSparePartId(Long sparePartId) { this.sparePartId = sparePartId; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }

    public static PurchaseOrderItemBuilder builder() { return new PurchaseOrderItemBuilder(); }

    public static class PurchaseOrderItemBuilder {
        private PurchaseOrder purchaseOrder;
        private Long sparePartId;
        private Integer quantity;
        private BigDecimal unitPrice, totalPrice;

        public PurchaseOrderItemBuilder purchaseOrder(PurchaseOrder v) { this.purchaseOrder = v; return this; }
        public PurchaseOrderItemBuilder sparePartId(Long v) { this.sparePartId = v; return this; }
        public PurchaseOrderItemBuilder quantity(Integer v) { this.quantity = v; return this; }
        public PurchaseOrderItemBuilder unitPrice(BigDecimal v) { this.unitPrice = v; return this; }
        public PurchaseOrderItemBuilder totalPrice(BigDecimal v) { this.totalPrice = v; return this; }
        public PurchaseOrderItem build() {
            PurchaseOrderItem i = new PurchaseOrderItem();
            i.purchaseOrder = purchaseOrder; i.sparePartId = sparePartId; i.quantity = quantity;
            i.unitPrice = unitPrice; i.totalPrice = totalPrice;
            return i;
        }
    }
}
