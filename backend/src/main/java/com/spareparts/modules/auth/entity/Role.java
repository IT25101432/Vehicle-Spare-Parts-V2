package com.spareparts.modules.auth.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private ERole name;

    public Role() {}

    public Role(Long id, ERole name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() { return this.id; }
    public void setId(Long id) { this.id = id; }
    public ERole getName() { return this.name; }
    public void setName(ERole name) { this.name = name; }
}