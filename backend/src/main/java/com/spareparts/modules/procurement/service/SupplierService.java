package com.spareparts.modules.procurement.service;

import com.spareparts.modules.procurement.dto.SupplierCreateRequest;
import com.spareparts.modules.procurement.dto.SupplierResponse;
import com.spareparts.modules.procurement.dto.SupplierUpdateRequest;

import java.util.List;

public interface SupplierService {
    SupplierResponse createSupplier(SupplierCreateRequest request);
    SupplierResponse getSupplierById(Long id);
    List<SupplierResponse> getAllSuppliers();
    SupplierResponse updateSupplier(Long id, SupplierUpdateRequest request);
    SupplierResponse toggleStatus(Long id);
    void deleteSupplier(Long id);
}
