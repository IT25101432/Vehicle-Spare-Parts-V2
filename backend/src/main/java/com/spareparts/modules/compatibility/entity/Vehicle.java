package com.spareparts.modules.compatibility.entity;

import com.spareparts.core.auditing.AuditableEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "vehicles", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"make", "model", "production_year"})
})
public class Vehicle extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String make;

    @Column(nullable = false)
    private String model;

    @Column(name = "production_year", nullable = false)
    private Integer year;

    public Vehicle() {
    }

    public Vehicle(Long id, String make, String model, Integer year) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.year = year;
    }

    private Vehicle(Builder builder) {
        this.id = builder.id;
        this.make = builder.make;
        this.model = builder.model;
        this.year = builder.year;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public static class Builder {
        private Long id;
        private String make;
        private String model;
        private Integer year;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder make(String make) {
            this.make = make;
            return this;
        }

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public Builder year(Integer year) {
            this.year = year;
            return this;
        }

        public Vehicle build() {
            return new Vehicle(this);
        }
    }
}
