package com.spareparts.modules.sales.service.impl;

import com.spareparts.core.exception.InsufficientStockException;
import com.spareparts.modules.inventory.entity.SparePart;
import com.spareparts.modules.inventory.repository.SparePartRepository;
import com.spareparts.modules.sales.dto.SaleCreateRequest;
import com.spareparts.modules.sales.dto.SaleResponse;
import com.spareparts.modules.sales.dto.SaleUpdateRequest;
import com.spareparts.modules.sales.entity.Sale;
import com.spareparts.modules.sales.entity.SaleItem;
import com.spareparts.modules.sales.entity.SaleStatus;
import com.spareparts.modules.sales.repository.SaleRepository;
import com.spareparts.modules.sales.service.SalesService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SalesServiceImpl implements SalesService {

    public SalesServiceImpl(SaleRepository saleRepository, SparePartRepository sparePartRepository) {
        this.saleRepository = saleRepository;
        this.sparePartRepository = sparePartRepository;
    }


    private final SaleRepository saleRepository;
    private final SparePartRepository sparePartRepository;

    @Override
    public SaleResponse createSale(SaleCreateRequest request) {
        Sale sale = Sale.builder()
                .invoiceNumber("INV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .saleDate(LocalDateTime.now())
                .status(SaleStatus.COMPLETED)
                .customerName(request.getCustomerName())
                .customerPhone(request.getCustomerPhone())
                .build();

        BigDecimal totalAmount = BigDecimal.ZERO;

        if (request.getItems() != null) {
            for (SaleCreateRequest.SaleItemDto itemDto : request.getItems()) {
                SparePart sparePart = sparePartRepository.findByPartNumber(itemDto.getPartNumber())
                        .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                                org.springframework.http.HttpStatus.NOT_FOUND, 
                                "Spare part not found with part number: " + itemDto.getPartNumber()
                        ));
                
                if (sparePart.getStockQuantity() < itemDto.getQuantity()) {
                    throw new InsufficientStockException("Insufficient stock for part: " + sparePart.getPartNumber());
                }

                // Deduct stock
                sparePart.setStockQuantity(sparePart.getStockQuantity() - itemDto.getQuantity());
                sparePartRepository.save(sparePart);

                BigDecimal itemTotal = itemDto.getUnitPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity()));
                totalAmount = totalAmount.add(itemTotal);

                SaleItem item = SaleItem.builder()
                        .sale(sale)
                        .sparePartId(sparePart.getId()) // save internal ID in SaleItem
                        .quantity(itemDto.getQuantity())
                        .unitPrice(itemDto.getUnitPrice())
                        .totalPrice(itemTotal)
                        .build();
                sale.getItems().add(item);
            }
        }

        sale.setTotalAmount(totalAmount);
        return mapToResponse(saleRepository.save(sale));
    }

    @Override
    public SaleResponse getSaleById(Long id) {
        Sale sale = saleRepository.findById(id).orElseThrow(() -> new RuntimeException("Sale not found"));
        return mapToResponse(sale);
    }

    @Override
    public List<SaleResponse> getAllSales() {
        return saleRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public SaleResponse updateSaleStatus(Long id, SaleUpdateRequest request) {
        Sale sale = saleRepository.findById(id).orElseThrow(() -> new RuntimeException("Sale not found"));
        if (request.getStatus() != null) {
            sale.setStatus(request.getStatus());
        }
        return mapToResponse(saleRepository.save(sale));
    }

    @Override
    public void deleteSaleRecord(Long id) {
        if (!saleRepository.existsById(id)) throw new RuntimeException("Sale not found");
        saleRepository.deleteById(id);
    }

    private SaleResponse mapToResponse(Sale sale) {
        List<SaleResponse.SaleItemResponse> itemResponses = sale.getItems().stream()
                .map(i -> SaleResponse.SaleItemResponse.builder()
                        .id(i.getId())
                        .sparePartId(i.getSparePartId())
                        .quantity(i.getQuantity())
                        .unitPrice(i.getUnitPrice())
                        .totalPrice(i.getTotalPrice())
                        .build())
                .collect(Collectors.toList());

        return SaleResponse.builder()
                .id(sale.getId())
                .invoiceNumber(sale.getInvoiceNumber())
                .saleDate(sale.getSaleDate())
                .totalAmount(sale.getTotalAmount())
                .status(sale.getStatus())
                .customerName(sale.getCustomerName())
                .customerPhone(sale.getCustomerPhone())
                .items(itemResponses)
                .build();
    }
}