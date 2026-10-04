package com.spareparts.modules.compatibility.entity;

import com.spareparts.modules.inventory.entity.SparePart;
import jakarta.persistence.*;

@Entity
@Table(name = "compatibility_mappings")
public class CompatibilityMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "part_number", nullable = false)
    private String partNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spare_part_id", nullable = false)
    private SparePart sparePart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    public CompatibilityMapping() {
    }

    public CompatibilityMapping(Long id, String partNumber, SparePart sparePart, Vehicle vehicle) {
        this.id = id;
        this.partNumber = partNumber;
        this.sparePart = sparePart;
        this.vehicle = vehicle;
    }

    private CompatibilityMapping(Builder builder) {
        this.id = builder.id;
        this.partNumber = builder.partNumber;
        this.sparePart = builder.sparePart;
        this.vehicle = builder.vehicle;
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

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public SparePart getSparePart() {
        return sparePart;
    }

    public void setSparePart(SparePart sparePart) {
        this.sparePart = sparePart;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public static class Builder {
        private Long id;
        private String partNumber;
        private SparePart sparePart;
        private Vehicle vehicle;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder partNumber(String partNumber) {
            this.partNumber = partNumber;
            return this;
        }

        public Builder sparePart(SparePart sparePart) {
            this.sparePart = sparePart;
            return this;
        }

        public Builder vehicle(Vehicle vehicle) {
            this.vehicle = vehicle;
            return this;
        }

        public CompatibilityMapping build() {
            return new CompatibilityMapping(this);
        }
    }
}
