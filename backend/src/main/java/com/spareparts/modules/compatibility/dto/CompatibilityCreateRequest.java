package com.spareparts.modules.compatibility.dto;

public class CompatibilityCreateRequest {
    private String partNumber;
    private Long vehicleId;

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }
}
