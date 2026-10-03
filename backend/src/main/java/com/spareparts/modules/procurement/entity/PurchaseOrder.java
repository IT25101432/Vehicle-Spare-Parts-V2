package com.spareparts.modules.procurement.entity;

import com.spareparts.core.auditing.AuditableEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "purchase_orders")
public class PurchaseOrder extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_number", nullable = false, unique = true)
    private String orderNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    @Column(name = "expected_delivery_date")
    private LocalDate expectedDeliveryDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseOrderItem> items = new ArrayList<>();

    public PurchaseOrder() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }
    public Supplier getSupplier() { return supplier; }
    public void setSupplier(Supplier supplier) { this.supplier = supplier; }
    public LocalDate getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate; }
    public LocalDate getExpectedDeliveryDate() { return expectedDeliveryDate; }
    public void setExpectedDeliveryDate(LocalDate expectedDeliveryDate) { this.expectedDeliveryDate = expectedDeliveryDate; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public List<PurchaseOrderItem> getItems() { return items; }
    public void setItems(List<PurchaseOrderItem> items) { this.items = items; }

    public static PurchaseOrderBuilder builder() { return new PurchaseOrderBuilder(); }

    public static class PurchaseOrderBuilder {
        private String orderNumber;
        private Supplier supplier;
        private LocalDate orderDate, expectedDeliveryDate;
        private OrderStatus status;
        private BigDecimal totalAmount;

        public PurchaseOrderBuilder orderNumber(String v) { this.orderNumber = v; return this; }
        public PurchaseOrderBuilder supplier(Supplier v) { this.supplier = v; return this; }
        public PurchaseOrderBuilder orderDate(LocalDate v) { this.orderDate = v; return this; }
        public PurchaseOrderBuilder expectedDeliveryDate(LocalDate v) { this.expectedDeliveryDate = v; return this; }
        public PurchaseOrderBuilder status(OrderStatus v) { this.status = v; return this; }
        public PurchaseOrderBuilder totalAmount(BigDecimal v) { this.totalAmount = v; return this; }
        public PurchaseOrder build() {
            PurchaseOrder p = new PurchaseOrder();
            p.orderNumber = orderNumber; p.supplier = supplier; p.orderDate = orderDate;
            p.expectedDeliveryDate = expectedDeliveryDate; p.status = status; p.totalAmount = totalAmount;
            return p;
        }
    }
}
