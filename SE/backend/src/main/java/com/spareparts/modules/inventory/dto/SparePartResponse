package com.spareparts.modules.inventory.dto;

import java.math.BigDecimal;

public class SparePartResponse {
    private Long id;
    private String partNumber;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private Integer reorderLevel;
    private String categoryName;
    private Long categoryId;

    public SparePartResponse() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPartNumber() { return partNumber; }
    public void setPartNumber(String partNumber) { this.partNumber = partNumber; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public Integer getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; }
    public Integer getReorderLevel() { return reorderLevel; }
    public void setReorderLevel(Integer reorderLevel) { this.reorderLevel = reorderLevel; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public static SparePartResponseBuilder builder() { return new SparePartResponseBuilder(); }

    public static class SparePartResponseBuilder {
        private Long id, categoryId;
        private String partNumber, name, description, categoryName;
        private BigDecimal price;
        private Integer stockQuantity, reorderLevel;

        public SparePartResponseBuilder id(Long id) { this.id = id; return this; }
        public SparePartResponseBuilder partNumber(String v) { this.partNumber = v; return this; }
        public SparePartResponseBuilder name(String v) { this.name = v; return this; }
        public SparePartResponseBuilder description(String v) { this.description = v; return this; }
        public SparePartResponseBuilder price(BigDecimal v) { this.price = v; return this; }
        public SparePartResponseBuilder stockQuantity(Integer v) { this.stockQuantity = v; return this; }
        public SparePartResponseBuilder reorderLevel(Integer v) { this.reorderLevel = v; return this; }
        public SparePartResponseBuilder categoryName(String v) { this.categoryName = v; return this; }
        public SparePartResponseBuilder categoryId(Long v) { this.categoryId = v; return this; }
        public SparePartResponse build() {
            SparePartResponse r = new SparePartResponse();
            r.id = id; r.partNumber = partNumber; r.name = name; r.description = description;
            r.price = price; r.stockQuantity = stockQuantity; r.reorderLevel = reorderLevel;
            r.categoryName = categoryName; r.categoryId = categoryId;
            return r;
        }
    }
}
