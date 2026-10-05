package com.spareparts.modules.sales.dto;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class SaleCreateRequest {

    private String customerName;

    @NotBlank(message = "Number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Number must be exactly 10 digits")
    private String customerPhone;

    private List<SaleItemDto> items;

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getCustomerPhone() { return customerPhone; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }
    public List<SaleItemDto> getItems() { return items; }
    public void setItems(List<SaleItemDto> items) { this.items = items; }

    public static class SaleItemDto {
        private String partNumber;
        private Integer quantity;
        private java.math.BigDecimal unitPrice;

        public String getPartNumber() { return partNumber; }
        public void setPartNumber(String partNumber) { this.partNumber = partNumber; }
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
        public java.math.BigDecimal getUnitPrice() { return unitPrice; }
        public void setUnitPrice(java.math.BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    }
}
