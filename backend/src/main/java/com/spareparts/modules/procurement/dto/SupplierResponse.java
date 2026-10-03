package com.spareparts.modules.procurement.dto;


public class SupplierResponse {
    private Long id;
    private String name;
    private String contactPerson;
    private String email;
    private String phone;
    private String address;
    private boolean isActive;

    public SupplierResponse() {}
    public SupplierResponse(Long id, String name, String contactPerson, String email, String phone, String address, boolean isActive) {
        this.id = id; this.name = name; this.contactPerson = contactPerson; this.email = email; this.phone = phone; this.address = address; this.isActive = isActive;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getContactPerson() { return contactPerson; }
    public void setContactPerson(String contactPerson) { this.contactPerson = contactPerson; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public boolean isActive() { return isActive; }
    public boolean getIsActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
    
    public static SupplierResponseBuilder builder() {
        return new SupplierResponseBuilder();
    }
    
    public static class SupplierResponseBuilder {
        private Long id; private String name; private String contactPerson; private String email; private String phone; private String address; private boolean isActive;
        public SupplierResponseBuilder id(Long id) { this.id = id; return this; }
        public SupplierResponseBuilder name(String name) { this.name = name; return this; }
        public SupplierResponseBuilder contactPerson(String contactPerson) { this.contactPerson = contactPerson; return this; }
        public SupplierResponseBuilder email(String email) { this.email = email; return this; }
        public SupplierResponseBuilder phone(String phone) { this.phone = phone; return this; }
        public SupplierResponseBuilder address(String address) { this.address = address; return this; }
        public SupplierResponseBuilder isActive(boolean isActive) { this.isActive = isActive; return this; }
        public SupplierResponse build() { return new SupplierResponse(id, name, contactPerson, email, phone, address, isActive); }
    }
}
