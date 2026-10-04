package com.spareparts.modules.procurement.service.impl;

import com.spareparts.core.exception.DuplicateResourceException;
import com.spareparts.core.exception.ResourceNotFoundException;
import com.spareparts.modules.procurement.dto.SupplierCreateRequest;
import com.spareparts.modules.procurement.dto.SupplierResponse;
import com.spareparts.modules.procurement.dto.SupplierUpdateRequest;
import com.spareparts.modules.procurement.entity.Supplier;
import com.spareparts.modules.procurement.repository.PurchaseOrderRepository;
import com.spareparts.modules.procurement.repository.SupplierRepository;
import com.spareparts.modules.procurement.service.SupplierService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;

    public SupplierServiceImpl(SupplierRepository supplierRepository,
                               PurchaseOrderRepository purchaseOrderRepository) {
        this.supplierRepository = supplierRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    @Override
    public SupplierResponse createSupplier(SupplierCreateRequest request) {
        if (supplierRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Supplier email already exists");
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
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found: " + id));
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
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found: " + id));

        // Partial update: only change a field if the client actually sent it.

        // Email: must be different from the current one and must not belong to ANOTHER supplier
        if (request.getEmail() != null && !request.getEmail().isBlank()
                && !request.getEmail().equals(supplier.getEmail())) {
            supplierRepository.findByEmail(request.getEmail())
                    .filter(other -> !other.getId().equals(id))
                    .ifPresent(other -> { throw new DuplicateResourceException("Supplier email already exists"); });
            supplier.setEmail(request.getEmail());
        }

        if (request.getName() != null && !request.getName().isBlank()) {
            supplier.setName(request.getName());
        }
        if (request.getContactPerson() != null) supplier.setContactPerson(request.getContactPerson());
        if (request.getPhone() != null)         supplier.setPhone(request.getPhone());
        if (request.getAddress() != null)       supplier.setAddress(request.getAddress());
        if (request.getIsActive() != null)      supplier.setActive(request.getIsActive());

        return mapToResponse(supplierRepository.save(supplier));
    }

    @Override
    public SupplierResponse toggleStatus(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found: " + id));
        supplier.setActive(!supplier.isActive());
        return mapToResponse(supplierRepository.save(supplier));
    }

    @Override
    public void deleteSupplier(Long id) {
        if (!supplierRepository.existsById(id)) {
            throw new ResourceNotFoundException("Supplier not found: " + id);
        }
        // Business rule: keep order history. Deactivate instead of deleting.
        if (purchaseOrderRepository.existsBySupplierId(id)) {
            throw new IllegalStateException(
                    "Cannot delete a supplier that has purchase orders. Deactivate the supplier instead.");
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
