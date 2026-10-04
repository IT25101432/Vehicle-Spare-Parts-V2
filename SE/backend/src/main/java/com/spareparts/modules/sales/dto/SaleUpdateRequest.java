package com.spareparts.modules.sales.dto;

import com.spareparts.modules.sales.entity.SaleStatus;

public class SaleUpdateRequest {
    private SaleStatus status;
    private String remarks;

    public SaleStatus getStatus() { return status; }
    public void setStatus(SaleStatus status) { this.status = status; }
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}