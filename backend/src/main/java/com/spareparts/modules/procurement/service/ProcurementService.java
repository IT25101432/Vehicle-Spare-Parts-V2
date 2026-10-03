package com.spareparts.modules.procurement.service;

import com.spareparts.modules.procurement.dto.PurchaseOrderCreateRequest;
import com.spareparts.modules.procurement.dto.PurchaseOrderResponse;
import com.spareparts.modules.procurement.dto.PurchaseOrderUpdateRequest;

import java.util.List;

public interface ProcurementService {
    PurchaseOrderResponse createPO(PurchaseOrderCreateRequest request);
    PurchaseOrderResponse getPOById(Long id);
    List<PurchaseOrderResponse> getAllPOs();
    PurchaseOrderResponse updatePOStatus(Long id, PurchaseOrderUpdateRequest request);
    void deletePO(Long id);
}
