package com.spareparts.modules.procurement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class SupplierCreateRequest {

    @NotBlank(message = "Name is required")
    private String name;

    private String contactPerson;

    @NotBlank(message = "Email is required")
    @Email(message = "Email format is invalid")
    private String email;

    // Optional field. Empty or null is allowed, but a filled value must look like a phone number.
    @Pattern(regexp = "^$|^[0-9+\\- ]{7,15}$", message = "Phone number is invalid")
    private String phone;

    private String address;

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
}
