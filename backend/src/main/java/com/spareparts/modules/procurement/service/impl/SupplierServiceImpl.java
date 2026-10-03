package com.spareparts.modules.procurement.service.impl;

import com.spareparts.modules.procurement.dto.SupplierCreateRequest;
import com.spareparts.modules.procurement.dto.SupplierResponse;
import com.spareparts.modules.procurement.dto.SupplierUpdateRequest;
import com.spareparts.modules.procurement.entity.Supplier;
import com.spareparts.modules.procurement.repository.SupplierRepository;
import com.spareparts.modules.procurement.service.SupplierService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }


    private final SupplierRepository supplierRepository;

    @Override
    public SupplierResponse createSupplier(SupplierCreateRequest request) {
        if (supplierRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Supplier email already exists");
        }

        Supplier supplier = Supplier.builder()
                .name(request.getName())
                .contactPerson(request.getContactPerson())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .isActive(true)
                .build();

        return mapToResponse(supplierRepository.save(supplier));
    }

    @Override
    public SupplierResponse getSupplierById(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found: " + id));
        return mapToResponse(supplier);
    }

    @Override
    public List<SupplierResponse> getAllSuppliers() {
        return supplierRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SupplierResponse updateSupplier(Long id, SupplierUpdateRequest request) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found: " + id));

        supplier.setName(request.getName());
        supplier.setContactPerson(request.getContactPerson());
        supplier.setEmail(request.getEmail());
        supplier.setPhone(request.getPhone());
        supplier.setAddress(request.getAddress());
        if (request.getIsActive() != null) {
            supplier.setActive(request.getIsActive());
        }

        return mapToResponse(supplierRepository.save(supplier));
    }

    @Override
    public SupplierResponse toggleStatus(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found: " + id));
        supplier.setActive(!supplier.isActive());
        return mapToResponse(supplierRepository.save(supplier));
    }

    @Override
    public void deleteSupplier(Long id) {
        if (!supplierRepository.existsById(id)) {
            throw new RuntimeException("Supplier not found: " + id);
        }
        supplierRepository.deleteById(id);
    }

    private SupplierResponse mapToResponse(Supplier supplier) {
        return SupplierResponse.builder()
                .id(supplier.getId())
                .name(supplier.getName())
                .contactPerson(supplier.getContactPerson())
                .email(supplier.getEmail())
                .phone(supplier.getPhone())
                .address(supplier.getAddress())
                .isActive(supplier.isActive())
                .build();
    }
}
