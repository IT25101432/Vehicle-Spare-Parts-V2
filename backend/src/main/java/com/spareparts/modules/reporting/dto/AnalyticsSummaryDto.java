package com.spareparts.modules.reporting.dto;

public class AnalyticsSummaryDto {
    private Integer lowStockItemsCount;
    private Integer pendingOrdersCount;

    public AnalyticsSummaryDto() {}

    public Integer getLowStockItemsCount() { return lowStockItemsCount; }
    public void setLowStockItemsCount(Integer lowStockItemsCount) { this.lowStockItemsCount = lowStockItemsCount; }
    public Integer getPendingOrdersCount() { return pendingOrdersCount; }
    public void setPendingOrdersCount(Integer pendingOrdersCount) { this.pendingOrdersCount = pendingOrdersCount; }

    public static AnalyticsSummaryDtoBuilder builder() { return new AnalyticsSummaryDtoBuilder(); }

    public static class AnalyticsSummaryDtoBuilder {
        private Integer lowStockItemsCount, pendingOrdersCount;
        public AnalyticsSummaryDtoBuilder lowStockItemsCount(Integer v) { this.lowStockItemsCount = v; return this; }
        public AnalyticsSummaryDtoBuilder pendingOrdersCount(Integer v) { this.pendingOrdersCount = v; return this; }
        public AnalyticsSummaryDto build() {
            AnalyticsSummaryDto d = new AnalyticsSummaryDto();
            d.lowStockItemsCount = lowStockItemsCount; d.pendingOrdersCount = pendingOrdersCount;
            return d;
        }
    }
}
