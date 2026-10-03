package com.spareparts.modules.inventory.service.impl;

import com.spareparts.modules.inventory.dto.SparePartCreateRequest;
import com.spareparts.modules.inventory.dto.SparePartResponse;
import com.spareparts.modules.inventory.dto.SparePartUpdateRequest;
import com.spareparts.modules.inventory.entity.Category;
import com.spareparts.modules.inventory.entity.SparePart;
import com.spareparts.modules.inventory.repository.CategoryRepository;
import com.spareparts.modules.inventory.repository.SparePartRepository;
import com.spareparts.modules.inventory.service.SparePartService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SparePartServiceImpl implements SparePartService {

    public SparePartServiceImpl(SparePartRepository sparePartRepository, CategoryRepository categoryRepository) {
        this.sparePartRepository = sparePartRepository;
        this.categoryRepository = categoryRepository;
    }


    private final SparePartRepository sparePartRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public SparePartResponse addPart(SparePartCreateRequest request) {
        Category category = null;
        if (request.getCategoryId() != null) {
            category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
        }

        var existingOpt = sparePartRepository.findByPartNumber(request.getPartNumber());
        if (existingOpt.isPresent()) {
            SparePart existing = existingOpt.get();
            existing.setStockQuantity(existing.getStockQuantity() + request.getStockQuantity());
            existing.setName(request.getName());
            existing.setDescription(request.getDescription());
            existing.setPrice(request.getPrice());
            existing.setReorderLevel(request.getReorderLevel());
            if (category != null) {
                existing.setCategory(category);
            }
            return mapToResponse(sparePartRepository.save(existing));
        }

        SparePart part = SparePart.builder()
                .partNumber(request.getPartNumber())
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .reorderLevel(request.getReorderLevel())
                .category(category)
                .build();

        return mapToResponse(sparePartRepository.save(part));
    }

    @Override
    public SparePartResponse getPartById(Long id) {
        SparePart part = sparePartRepository.findById(id).orElseThrow(() -> new RuntimeException("Part not found"));
        return mapToResponse(part);
    }

    @Override
    public List<SparePartResponse> searchParts(String keyword) {
        // Simplified search returning all for now, in a real scenario use custom queries
        return sparePartRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public SparePartResponse updatePart(Long id, SparePartUpdateRequest request) {
        SparePart part = sparePartRepository.findById(id).orElseThrow(() -> new RuntimeException("Part not found"));

        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            part.setCategory(category);
        }

        if (request.getPartNumber() != null && !request.getPartNumber().equals(part.getPartNumber())) {
            if (sparePartRepository.existsByPartNumber(request.getPartNumber())) {
                throw new RuntimeException("Part number already exists");
            }
            part.setPartNumber(request.getPartNumber());
        }
        part.setName(request.getName());
        part.setDescription(request.getDescription());
        part.setPrice(request.getPrice());
        part.setStockQuantity(request.getStockQuantity());
        part.setReorderLevel(request.getReorderLevel());

        return mapToResponse(sparePartRepository.save(part));
    }

    @Override
    public void deletePart(Long id) {
        if (!sparePartRepository.existsById(id)) throw new RuntimeException("Part not found");
        sparePartRepository.deleteById(id);
    }

    private SparePartResponse mapToResponse(SparePart part) {
        return SparePartResponse.builder()
                .id(part.getId())
                .partNumber(part.getPartNumber())
                .name(part.getName())
                .description(part.getDescription())
                .price(part.getPrice())
                .stockQuantity(part.getStockQuantity())
                .reorderLevel(part.getReorderLevel())
                .categoryId(part.getCategory() != null ? part.getCategory().getId() : null)
                .categoryName(part.getCategory() != null ? part.getCategory().getName() : null)
                .build();
    }
}
