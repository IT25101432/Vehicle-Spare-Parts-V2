package com.spareparts.modules.compatibility.service.impl;

import com.spareparts.modules.compatibility.dto.*;
import com.spareparts.modules.compatibility.entity.CompatibilityMapping;
import com.spareparts.modules.compatibility.entity.Vehicle;
import com.spareparts.modules.compatibility.repository.CompatibilityRepository;
import com.spareparts.modules.compatibility.repository.VehicleRepository;
import com.spareparts.modules.compatibility.service.CompatibilityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CompatibilityServiceImpl implements CompatibilityService {

    public CompatibilityServiceImpl(VehicleRepository vehicleRepository, CompatibilityRepository compatibilityRepository) {
        this.vehicleRepository = vehicleRepository;
        this.compatibilityRepository = compatibilityRepository;
    }


    private final VehicleRepository vehicleRepository;
    private final CompatibilityRepository compatibilityRepository;

    @Override
    public VehicleResponse createVehicle(VehicleCreateRequest request) {
        if (vehicleRepository.existsByMakeAndModelAndYear(request.getMake(), request.getModel(), request.getYear())) {
            throw new RuntimeException("Vehicle already exists");
        }
        Vehicle vehicle = Vehicle.builder()
                .make(request.getMake())
                .model(request.getModel())
                .year(request.getYear())
                .build();
        return mapToResponse(vehicleRepository.save(vehicle));
    }

    @Override
    public List<VehicleResponse> getAllVehicles() {
        return vehicleRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public VehicleResponse getVehicleById(Long id) {
        return mapToResponse(vehicleRepository.findById(id).orElseThrow(() -> new RuntimeException("Vehicle not found")));
    }

    @Override
    public VehicleResponse updateVehicle(Long id, VehicleUpdateRequest request) {
        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(() -> new RuntimeException("Vehicle not found"));
        vehicle.setMake(request.getMake());
        vehicle.setModel(request.getModel());
        vehicle.setYear(request.getYear());
        return mapToResponse(vehicleRepository.save(vehicle));
    }

    @Override
    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }

    @Override
    public CompatibilityResponse addMapping(CompatibilityCreateRequest request) {
        if (compatibilityRepository.existsByPartNumberAndVehicleId(request.getPartNumber(), request.getVehicleId())) {
            throw new RuntimeException("Mapping already exists");
        }
        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        
        CompatibilityMapping mapping = CompatibilityMapping.builder()
                .partNumber(request.getPartNumber())
                .vehicle(vehicle)
                .build();
        return mapToResponse(compatibilityRepository.save(mapping));
    }

    @Override
    public List<CompatibilityResponse> getMappingsByPart(String partNumber) {
        if (partNumber == null || partNumber.isEmpty() || partNumber.equals("0")) {
            return compatibilityRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
        }
        return compatibilityRepository.findByPartNumber(partNumber).stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public List<CompatibilityResponse> getMappingsByVehicle(Long vehicleId) {
        return compatibilityRepository.findByVehicleId(vehicleId).stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public void deleteMapping(Long id) {
        compatibilityRepository.deleteById(id);
    }

    private VehicleResponse mapToResponse(Vehicle v) {
        return VehicleResponse.builder()
                .id(v.getId())
                .make(v.getMake())
                .model(v.getModel())
                .year(v.getYear())
                .build();
    }

    private CompatibilityResponse mapToResponse(CompatibilityMapping m) {
        return CompatibilityResponse.builder()
                .id(m.getId())
                .partNumber(m.getPartNumber())
                .vehicle(mapToResponse(m.getVehicle()))
                .build();
    }
}
