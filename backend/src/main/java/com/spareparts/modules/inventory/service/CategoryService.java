package com.spareparts.modules.inventory.service;

import com.spareparts.modules.inventory.dto.CategoryRequest;
import com.spareparts.modules.inventory.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest request);
    CategoryResponse getCategoryById(Long id);
    List<CategoryResponse> getAllCategories();
    CategoryResponse updateCategory(Long id, CategoryRequest request);
    void deleteCategory(Long id);
}
