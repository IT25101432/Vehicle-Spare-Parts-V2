package com.spareparts.modules.compatibility.dto;

public class CompatibilityResponse {
    private Long id;
    private String partNumber;
    private VehicleResponse vehicle;

    public CompatibilityResponse() {
    }

    private CompatibilityResponse(Builder builder) {
        this.id = builder.id;
        this.partNumber = builder.partNumber;
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

    public VehicleResponse getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleResponse vehicle) {
        this.vehicle = vehicle;
    }

    public static class Builder {
        private Long id;
        private String partNumber;
        private VehicleResponse vehicle;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder partNumber(String partNumber) {
            this.partNumber = partNumber;
            return this;
        }

        public Builder vehicle(VehicleResponse vehicle) {
            this.vehicle = vehicle;
            return this;
        }

        public CompatibilityResponse build() {
            return new CompatibilityResponse(this);
        }
    }
}
