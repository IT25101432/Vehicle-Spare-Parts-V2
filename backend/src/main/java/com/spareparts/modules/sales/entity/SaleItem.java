package com.spareparts.modules.sales.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "sale_items")
public class SaleItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    @Column(name = "spare_part_id", nullable = false)
    private Long sparePartId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    public SaleItem() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Sale getSale() { return sale; }
    public void setSale(Sale sale) { this.sale = sale; }
    public Long getSparePartId() { return sparePartId; }
    public void setSparePartId(Long sparePartId) { this.sparePartId = sparePartId; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }

    public static SaleItemBuilder builder() { return new SaleItemBuilder(); }

    public static class SaleItemBuilder {
        private Sale sale;
        private Long sparePartId;
        private Integer quantity;
        private BigDecimal unitPrice;
        private BigDecimal totalPrice;

        public SaleItemBuilder sale(Sale sale) { this.sale = sale; return this; }
        public SaleItemBuilder sparePartId(Long sparePartId) { this.sparePartId = sparePartId; return this; }
        public SaleItemBuilder quantity(Integer quantity) { this.quantity = quantity; return this; }
        public SaleItemBuilder unitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; return this; }
        public SaleItemBuilder totalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; return this; }
        public SaleItem build() {
            SaleItem i = new SaleItem();
            i.sale = sale; i.sparePartId = sparePartId; i.quantity = quantity;
            i.unitPrice = unitPrice; i.totalPrice = totalPrice;
            return i;
        }
    }
}
