package com.spareparts.modules.sales.dto;

import com.spareparts.modules.sales.entity.SaleStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class SaleResponse {
    private Long id;
    private String invoiceNumber;
    private LocalDateTime saleDate;
    private BigDecimal totalAmount;
    private SaleStatus status;
    private String customerName;
    private String customerPhone;
    private List<SaleItemResponse> items;

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
    public List<SaleItemResponse> getItems() { return items; }
    public void setItems(List<SaleItemResponse> items) { this.items = items; }

    public static SaleResponseBuilder builder() { return new SaleResponseBuilder(); }

    public static class SaleResponseBuilder {
        private Long id;
        private String invoiceNumber;
        private LocalDateTime saleDate;
        private BigDecimal totalAmount;
        private SaleStatus status;
        private String customerName;
        private String customerPhone;
        private List<SaleItemResponse> items;

        public SaleResponseBuilder id(Long id) { this.id = id; return this; }
        public SaleResponseBuilder invoiceNumber(String invoiceNumber) { this.invoiceNumber = invoiceNumber; return this; }
        public SaleResponseBuilder saleDate(LocalDateTime saleDate) { this.saleDate = saleDate; return this; }
        public SaleResponseBuilder totalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; return this; }
        public SaleResponseBuilder status(SaleStatus status) { this.status = status; return this; }
        public SaleResponseBuilder customerName(String customerName) { this.customerName = customerName; return this; }
        public SaleResponseBuilder customerPhone(String customerPhone) { this.customerPhone = customerPhone; return this; }
        public SaleResponseBuilder items(List<SaleItemResponse> items) { this.items = items; return this; }
        public SaleResponse build() {
            SaleResponse r = new SaleResponse();
            r.id = id; r.invoiceNumber = invoiceNumber; r.saleDate = saleDate;
            r.totalAmount = totalAmount; r.status = status; r.customerName = customerName;
            r.customerPhone = customerPhone; r.items = items;
            return r;
        }
    }

    public static class SaleItemResponse {
        private Long id;
        private Long sparePartId;
        private Integer quantity;
        private BigDecimal unitPrice;
        private BigDecimal totalPrice;

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

        public static SaleItemResponseBuilder builder() { return new SaleItemResponseBuilder(); }

        public static class SaleItemResponseBuilder {
            private Long id;
            private Long sparePartId;
            private Integer quantity;
            private BigDecimal unitPrice;
            private BigDecimal totalPrice;

            public SaleItemResponseBuilder id(Long id) { this.id = id; return this; }
            public SaleItemResponseBuilder sparePartId(Long sparePartId) { this.sparePartId = sparePartId; return this; }
            public SaleItemResponseBuilder quantity(Integer quantity) { this.quantity = quantity; return this; }
            public SaleItemResponseBuilder unitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; return this; }
            public SaleItemResponseBuilder totalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; return this; }
            public SaleItemResponse build() {
                SaleItemResponse r = new SaleItemResponse();
                r.id = id; r.sparePartId = sparePartId; r.quantity = quantity;
                r.unitPrice = unitPrice; r.totalPrice = totalPrice;
                return r;
            }
        }
    }
}
