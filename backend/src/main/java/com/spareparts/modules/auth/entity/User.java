package com.spareparts.modules.auth.entity;

import com.spareparts.core.auditing.AuditableEntity;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Entity
@Table(name = "users")
public class User extends AuditableEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(nullable = false, columnDefinition = "BIT DEFAULT 1")
    private boolean enabled;

    public User() {}

    @PrePersist
    private void prePersist() {
        if (!this.enabled) {
            this.enabled = true;
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public void setPassword(String password) { this.password = password; }
    public void setUsername(String username) { this.username = username; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role == null) return java.util.Collections.emptyList();
        return java.util.Collections.singletonList(new SimpleGrantedAuthority(role.getName().name()));
    }

    @Override
    public String getPassword() { return this.password; }
    @Override
    public String getUsername() { return this.username; }
    @Override
    public boolean isEnabled() { return this.enabled; }
    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }

    public static UserBuilder builder() { return new UserBuilder(); }
    public static class UserBuilder {
        private String username, email, password, firstName, lastName;
        private Role role;
        private boolean enabled;
        public UserBuilder username(String v) { this.username = v; return this; }
        public UserBuilder email(String v) { this.email = v; return this; }
        public UserBuilder password(String v) { this.password = v; return this; }
        public UserBuilder firstName(String v) { this.firstName = v; return this; }
        public UserBuilder lastName(String v) { this.lastName = v; return this; }
        public UserBuilder role(Role v) { this.role = v; return this; }
        public UserBuilder enabled(boolean v) { this.enabled = v; return this; }
        public User build() {
            User u = new User();
            u.username = username; u.email = email; u.password = password;
            u.firstName = firstName; u.lastName = lastName;
            u.role = role; u.enabled = enabled;
            return u;
        }
    }
}