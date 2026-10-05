package com.spareparts.modules.procurement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

// All fields are optional (partial update), so there is no @NotBlank here.
// Rules are only checked when a value is actually sent.
public class SupplierUpdateRequest {
    private String name;
    private String contactPerson;

    @Email(message = "Email format is invalid")
    private String email;

    @Pattern(regexp = "^$|^[0-9+\\- ]{7,15}$", message = "Phone number is invalid")
    private String phone;

    private String address;
    private Boolean isActive;

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
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}
