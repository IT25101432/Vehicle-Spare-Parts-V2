package com.spareparts.modules.sales.entity;

import com.spareparts.core.auditing.AuditableEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sales")
public class Sale extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "invoice_number", nullable = false, unique = true)
    private String invoiceNumber;

    @Column(name = "sale_date", nullable = false)
    private LocalDateTime saleDate;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SaleStatus status;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_phone")
    private String customerPhone;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SaleItem> items = new ArrayList<>();

    public Sale() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getInvoiceNumber() { return invoiceNumber; }
    public void setInvoiceNumber(String invoiceNumber) { this.invoiceNumber = invoiceNumber; }
    public LocalDateTime getSaleDate() { return saleDate; }
    public void setSaleDate(LocalDateTime saleDate) { this.saleDate = saleDate; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public SaleStatus getStatus() { return status; }
    public void setStatus(SaleStatus status) { this.status = status; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getCustomerPhone() { return customerPhone; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }
    public List<SaleItem> getItems() { return items; }
    public void setItems(List<SaleItem> items) { this.items = items; }

    public static SaleBuilder builder() { return new SaleBuilder(); }

    public static class SaleBuilder {
        private String invoiceNumber;
        private LocalDateTime saleDate;
        private BigDecimal totalAmount;
        private SaleStatus status;
        private String customerName;
        private String customerPhone;

        public SaleBuilder invoiceNumber(String invoiceNumber) { this.invoiceNumber = invoiceNumber; return this; }
        public SaleBuilder saleDate(LocalDateTime saleDate) { this.saleDate = saleDate; return this; }
        public SaleBuilder totalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; return this; }
        public SaleBuilder status(SaleStatus status) { this.status = status; return this; }
        public SaleBuilder customerName(String customerName) { this.customerName = customerName; return this; }
        public SaleBuilder customerPhone(String customerPhone) { this.customerPhone = customerPhone; return this; }
        public Sale build() {
            Sale s = new Sale();
            s.invoiceNumber = invoiceNumber; s.saleDate = saleDate; s.totalAmount = totalAmount;
            s.status = status; s.customerName = customerName; s.customerPhone = customerPhone;
            return s;
        }
    }
}
