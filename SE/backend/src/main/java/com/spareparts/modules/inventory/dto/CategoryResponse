package com.spareparts.modules.inventory.dto;

public class CategoryResponse {
    private Long id;
    private String name;
    private String description;

    public CategoryResponse() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public static CategoryResponseBuilder builder() { return new CategoryResponseBuilder(); }

    public static class CategoryResponseBuilder {
        private Long id;
        private String name, description;

        public CategoryResponseBuilder id(Long id) { this.id = id; return this; }
        public CategoryResponseBuilder name(String name) { this.name = name; return this; }
        public CategoryResponseBuilder description(String description) { this.description = description; return this; }
        public CategoryResponse build() {
            CategoryResponse r = new CategoryResponse();
            r.id = id; r.name = name; r.description = description;
            return r;
        }
    }
}