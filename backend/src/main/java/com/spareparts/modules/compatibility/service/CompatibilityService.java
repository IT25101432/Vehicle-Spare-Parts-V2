package com.spareparts.modules.compatibility.service;

import com.spareparts.modules.compatibility.dto.*;
import java.util.List;

public interface CompatibilityService {
    VehicleResponse createVehicle(VehicleCreateRequest request);
    List<VehicleResponse> getAllVehicles();
    VehicleResponse getVehicleById(Long id);
    VehicleResponse updateVehicle(Long id, VehicleUpdateRequest request);
    void deleteVehicle(Long id);

    CompatibilityResponse addMapping(CompatibilityCreateRequest request);
    List<CompatibilityResponse> getMappingsByPart(String partNumber);
    List<CompatibilityResponse> getMappingsByVehicle(Long vehicleId);
    void deleteMapping(Long id);
}
