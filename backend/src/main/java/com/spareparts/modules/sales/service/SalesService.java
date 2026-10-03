package com.spareparts.modules.sales.service;

import com.spareparts.modules.sales.dto.SaleCreateRequest;
import com.spareparts.modules.sales.dto.SaleResponse;
import com.spareparts.modules.sales.dto.SaleUpdateRequest;

import java.util.List;

public interface SalesService {
    SaleResponse createSale(SaleCreateRequest request);
    SaleResponse getSaleById(Long id);
    List<SaleResponse> getAllSales();
    SaleResponse updateSaleStatus(Long id, SaleUpdateRequest request);
    void deleteSaleRecord(Long id);
}
