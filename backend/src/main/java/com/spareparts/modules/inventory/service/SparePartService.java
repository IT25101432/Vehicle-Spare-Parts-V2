package com.spareparts.modules.inventory.service;

import com.spareparts.modules.inventory.dto.SparePartCreateRequest;
import com.spareparts.modules.inventory.dto.SparePartResponse;
import com.spareparts.modules.inventory.dto.SparePartUpdateRequest;

import java.util.List;

public interface SparePartService {
    SparePartResponse addPart(SparePartCreateRequest request);
    SparePartResponse getPartById(Long id);
    List<SparePartResponse> searchParts(String keyword);
    SparePartResponse updatePart(Long id, SparePartUpdateRequest request);
    void deletePart(Long id);
}
