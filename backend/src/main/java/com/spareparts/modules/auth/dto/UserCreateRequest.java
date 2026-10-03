package com.spareparts.modules.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserCreateRequest {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 100, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name cannot exceed 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name cannot exceed 50 characters")
    private String lastName;

    private String role;

    public UserCreateRequest() {}

    public UserCreateRequest(String username, String password, String email, String firstName, String lastName, String role) {
        this.username = username; this.password = password; this.email = email;
        this.firstName = firstName; this.lastName = lastName; this.role = role;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public static UserCreateRequestBuilder builder() { return new UserCreateRequestBuilder(); }

    public static class UserCreateRequestBuilder {
        private String username, password, email, firstName, lastName, role;
        public UserCreateRequestBuilder username(String v) { this.username = v; return this; }
        public UserCreateRequestBuilder password(String v) { this.password = v; return this; }
        public UserCreateRequestBuilder email(String v) { this.email = v; return this; }
        public UserCreateRequestBuilder firstName(String v) { this.firstName = v; return this; }
        public UserCreateRequestBuilder lastName(String v) { this.lastName = v; return this; }
        public UserCreateRequestBuilder role(String v) { this.role = v; return this; }
        public UserCreateRequest build() { return new UserCreateRequest(username, password, email, firstName, lastName, role); }
    }
}