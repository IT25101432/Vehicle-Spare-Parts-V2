package com.spareparts.modules.procurement.entity;

import com.spareparts.core.auditing.AuditableEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "suppliers")
public class Supplier extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "contact_person")
    private String contactPerson;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    private String address;

    @Column(name = "is_active")
    private boolean isActive = true;

    public boolean isActive() { return isActive; }
    public void setActive(boolean isActive) { this.isActive = isActive; }
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getContactPerson() { return contactPerson; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public void setName(String name) { this.name = name; }
    public void setContactPerson(String contactPerson) { this.contactPerson = contactPerson; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setAddress(String address) { this.address = address; }

    public static SupplierBuilder builder() { return new SupplierBuilder(); }
    public static class SupplierBuilder {
        private String name; private String contactPerson; private String email; private String phone; private String address; private boolean isActive = true;
        public SupplierBuilder name(String name) { this.name = name; return this; }
        public SupplierBuilder contactPerson(String contactPerson) { this.contactPerson = contactPerson; return this; }
        public SupplierBuilder email(String email) { this.email = email; return this; }
        public SupplierBuilder phone(String phone) { this.phone = phone; return this; }
        public SupplierBuilder address(String address) { this.address = address; return this; }
        public SupplierBuilder isActive(boolean isActive) { this.isActive = isActive; return this; }
        public Supplier build() {
            Supplier s = new Supplier();
            s.setName(name); s.setContactPerson(contactPerson); s.setEmail(email);
            s.setPhone(phone); s.setAddress(address); s.setActive(isActive);
            return s;
        }
    }
}
