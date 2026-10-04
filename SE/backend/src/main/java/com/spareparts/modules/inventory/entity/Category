package com.spareparts.modules.inventory.entity;

import com.spareparts.core.auditing.AuditableEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    public Category() {}

    public Category(Long id, String name, String description) {
        this.id = id; this.name = name; this.description = description;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public static CategoryBuilder builder() { return new CategoryBuilder(); }

    public static class CategoryBuilder {
        private String name, description;
        public CategoryBuilder name(String name) { this.name = name; return this; }
        public CategoryBuilder description(String description) { this.description = description; return this; }
        public Category build() {
            Category c = new Category();
            c.name = name; c.description = description;
            return c;
        }
    }
}
