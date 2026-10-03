package com.spareparts.modules.auth.dto;

public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private boolean enabled;
    private String role;

    public UserResponse() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public static UserResponseBuilder builder() { return new UserResponseBuilder(); }

    public static class UserResponseBuilder {
        private Long id;
        private String username, email, firstName, lastName, role;
        private boolean enabled;

        public UserResponseBuilder id(Long id) { this.id = id; return this; }
        public UserResponseBuilder username(String v) { this.username = v; return this; }
        public UserResponseBuilder email(String v) { this.email = v; return this; }
        public UserResponseBuilder firstName(String v) { this.firstName = v; return this; }
        public UserResponseBuilder lastName(String v) { this.lastName = v; return this; }
        public UserResponseBuilder enabled(boolean v) { this.enabled = v; return this; }
        public UserResponseBuilder role(String v) { this.role = v; return this; }
        public UserResponse build() {
            UserResponse r = new UserResponse();
            r.id = id; r.username = username; r.email = email;
            r.firstName = firstName; r.lastName = lastName;
            r.enabled = enabled; r.role = role;
            return r;
        }
    }
}