package com.spareparts.modules.procurement.service.impl;

import com.spareparts.core.exception.ResourceNotFoundException;
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
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProcurementServiceImpl implements ProcurementService {

    private final PurchaseOrderRepository poRepository;
    private final SupplierRepository supplierRepository;
    private final SparePartRepository sparePartRepository;

    public ProcurementServiceImpl(PurchaseOrderRepository poRepository,
                                  SupplierRepository supplierRepository,
                                  SparePartRepository sparePartRepository) {
        this.poRepository = poRepository;
        this.supplierRepository = supplierRepository;
        this.sparePartRepository = sparePartRepository;
    }

    @Override
    public PurchaseOrderResponse createPO(PurchaseOrderCreateRequest request) {
        Supplier supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found: " + request.getSupplierId()));

        // Business rule: no orders for deactivated suppliers
        if (!supplier.isActive()) {
            throw new IllegalStateException("Cannot create an order for an inactive supplier");
        }

        PurchaseOrder po = PurchaseOrder.builder()
                .orderNumber("PO-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .supplier(supplier)
                .orderDate(LocalDate.now())
                .expectedDeliveryDate(request.getExpectedDeliveryDate())
                .status(OrderStatus.PENDING)
                .build();

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (PurchaseOrderCreateRequest.PurchaseOrderItemDto itemDto : request.getItems()) {
            // Business rule: the spare part must exist (checked now, not later at RECEIVED time)
            if (!sparePartRepository.existsById(itemDto.getSparePartId())) {
                throw new ResourceNotFoundException("Spare part not found: " + itemDto.getSparePartId());
            }

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

        po.setTotalAmount(totalAmount);
        return mapToResponse(poRepository.save(po));
    }

    @Override
    public PurchaseOrderResponse getPOById(Long id) {
        PurchaseOrder po = poRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase Order not found: " + id));
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
                .orElseThrow(() -> new ResourceNotFoundException("Purchase Order not found: " + id));

        OrderStatus current = po.getStatus();
        OrderStatus requested = request.getStatus();
        boolean statusChanging = requested != null && requested != current;

        // 1. Validate the status transition
        if (statusChanging && !current.canTransitionTo(requested)) {
            throw new IllegalStateException(
                    "Cannot change order status from " + current + " to " + requested);
        }

        // 2. A finished order's delivery date cannot be changed
        LocalDate newDate = request.getExpectedDeliveryDate();
        if (newDate != null && !newDate.equals(po.getExpectedDeliveryDate())) {
            if (current == OrderStatus.RECEIVED || current == OrderStatus.CANCELLED) {
                throw new IllegalStateException("Cannot change the delivery date of a " + current + " order");
            }
            po.setExpectedDeliveryDate(newDate);
        }

        // 3. Apply the status change
        if (statusChanging) {
            po.setStatus(requested);

            // Stock is added only when moving to RECEIVED. RECEIVED is a final state,
            // so this can happen at most once per order.
            if (requested == OrderStatus.RECEIVED) {
                for (PurchaseOrderItem item : po.getItems()) {
                    SparePart part = sparePartRepository.findById(item.getSparePartId())
                            .orElseThrow(() -> new ResourceNotFoundException("Spare part not found: " + item.getSparePartId()));
                    part.setStockQuantity(part.getStockQuantity() + item.getQuantity());
                    sparePartRepository.save(part);
                }
            }
        }

        return mapToResponse(poRepository.save(po));
    }

    @Override
    public void deletePO(Long id) {
        if (!poRepository.existsById(id)) {
            throw new ResourceNotFoundException("Purchase Order not found: " + id);
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
