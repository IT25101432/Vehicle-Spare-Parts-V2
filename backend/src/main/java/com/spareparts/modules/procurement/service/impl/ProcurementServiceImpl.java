package com.spareparts.modules.procurement.service.impl;

import com.spareparts.modules.inventory.entity.SparePart;
import com.spareparts.modules.inventory.repository.SparePartRepository;
import com.spareparts.modules.procurement.dto.PurchaseOrderCreateRequest;
import com.spareparts.modules.procurement.dto.PurchaseOrderResponse;
import com.spareparts.modules.procurement.dto.PurchaseOrderUpdateRequest;
import com.spareparts.modules.procurement.entity.OrderStatus;
import com.spareparts.modules.procurement.entity.PurchaseOrder;
import com.spareparts.modules.procurement.entity.PurchaseOrderItem;
import com.spareparts.modules.procurement.entity.Supplier;
import com.spareparts.modules.procurement.repository.PurchaseOrderRepository;
import com.spareparts.modules.procurement.repository.SupplierRepository;
import com.spareparts.modules.procurement.service.ProcurementService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProcurementServiceImpl implements ProcurementService {

    public ProcurementServiceImpl(PurchaseOrderRepository poRepository, SupplierRepository supplierRepository, SparePartRepository sparePartRepository) {
        this.poRepository = poRepository;
        this.supplierRepository = supplierRepository;
        this.sparePartRepository = sparePartRepository;
    }


    private final PurchaseOrderRepository poRepository;
    private final SupplierRepository supplierRepository;
    private final SparePartRepository sparePartRepository;

    @Override
    public PurchaseOrderResponse createPO(PurchaseOrderCreateRequest request) {
        Supplier supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found: " + request.getSupplierId()));

        PurchaseOrder po = PurchaseOrder.builder()
                .orderNumber("PO-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .supplier(supplier)
                .orderDate(LocalDate.now())
                .expectedDeliveryDate(request.getExpectedDeliveryDate())
                .status(OrderStatus.PENDING)
                .build();

        BigDecimal totalAmount = BigDecimal.ZERO;
        
        if (request.getItems() != null) {
            for (PurchaseOrderCreateRequest.PurchaseOrderItemDto itemDto : request.getItems()) {
                BigDecimal itemTotal = itemDto.getUnitPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity()));
                totalAmount = totalAmount.add(itemTotal);
                
                PurchaseOrderItem poi = PurchaseOrderItem.builder()
                        .purchaseOrder(po)
                        .sparePartId(itemDto.getSparePartId())
                        .quantity(itemDto.getQuantity())
                        .unitPrice(itemDto.getUnitPrice())
                        .totalPrice(itemTotal)
                        .build();
                po.getItems().add(poi);
            }
        }
        
        po.setTotalAmount(totalAmount);
        return mapToResponse(poRepository.save(po));
    }

    @Override
    public PurchaseOrderResponse getPOById(Long id) {
        PurchaseOrder po = poRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase Order not found: " + id));
        return mapToResponse(po);
    }

    @Override
    public List<PurchaseOrderResponse> getAllPOs() {
        return poRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PurchaseOrderResponse updatePOStatus(Long id, PurchaseOrderUpdateRequest request) {
        PurchaseOrder po = poRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase Order not found: " + id));

        // If transitioning to RECEIVED, add items to stock
        boolean isNewlyDelivered = request.getStatus() != null 
                && request.getStatus() == OrderStatus.RECEIVED 
                && po.getStatus() != OrderStatus.RECEIVED;

        if (request.getStatus() != null) {
            po.setStatus(request.getStatus());
        }
        if (request.getExpectedDeliveryDate() != null) {
            po.setExpectedDeliveryDate(request.getExpectedDeliveryDate());
        }

        if (isNewlyDelivered) {
            for (PurchaseOrderItem item : po.getItems()) {
                SparePart part = sparePartRepository.findById(item.getSparePartId())
                        .orElseThrow(() -> new RuntimeException("Spare part not found: " + item.getSparePartId()));
                part.setStockQuantity(part.getStockQuantity() + item.getQuantity());
                sparePartRepository.save(part);
            }
        }

        return mapToResponse(poRepository.save(po));
    }

    @Override
    public void deletePO(Long id) {
        if (!poRepository.existsById(id)) {
            throw new RuntimeException("Purchase Order not found: " + id);
        }
        poRepository.deleteById(id);
    }

    private PurchaseOrderResponse mapToResponse(PurchaseOrder po) {
        List<PurchaseOrderResponse.PurchaseOrderItemResponse> itemResponses = po.getItems().stream()
                .map(item -> PurchaseOrderResponse.PurchaseOrderItemResponse.builder()
                        .id(item.getId())
                        .sparePartId(item.getSparePartId())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .totalPrice(item.getTotalPrice())
                        .build())
                .collect(Collectors.toList());

        return PurchaseOrderResponse.builder()
                .id(po.getId())
                .orderNumber(po.getOrderNumber())
                .supplierId(po.getSupplier().getId())
                .supplierName(po.getSupplier().getName())
                .orderDate(po.getOrderDate())
                .expectedDeliveryDate(po.getExpectedDeliveryDate())
                .status(po.getStatus())
                .totalAmount(po.getTotalAmount())
                .items(itemResponses)
                .build();
    }
}
