package com.spareparts.modules.inventory.entity;

import com.spareparts.core.auditing.AuditableEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "spare_parts")
public class SparePart extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "part_number", nullable = false, unique = true)
    private String partNumber;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity;

    @Column(name = "reorder_level", nullable = false)
    private Integer reorderLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public SparePart() {}

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
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public static SparePartBuilder builder() { return new SparePartBuilder(); }

    public static class SparePartBuilder {
        private String partNumber, name, description;
        private BigDecimal price;
        private Integer stockQuantity, reorderLevel;
        private Category category;

        public SparePartBuilder partNumber(String v) { this.partNumber = v; return this; }
        public SparePartBuilder name(String v) { this.name = v; return this; }
        public SparePartBuilder description(String v) { this.description = v; return this; }
        public SparePartBuilder price(BigDecimal v) { this.price = v; return this; }
        public SparePartBuilder stockQuantity(Integer v) { this.stockQuantity = v; return this; }
        public SparePartBuilder reorderLevel(Integer v) { this.reorderLevel = v; return this; }
        public SparePartBuilder category(Category v) { this.category = v; return this; }
        public SparePart build() {
            SparePart s = new SparePart();
            s.partNumber = partNumber; s.name = name; s.description = description;
            s.price = price; s.stockQuantity = stockQuantity; s.reorderLevel = reorderLevel;
            s.category = category;
            return s;
        }
    }
}
