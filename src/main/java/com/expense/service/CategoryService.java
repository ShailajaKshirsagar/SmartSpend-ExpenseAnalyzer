package com.expense.service;

import com.expense.dtos.CategoryRequestDto;
import com.expense.entity.Category;

import java.util.List;

public interface CategoryService {
    String saveCategory(Long userId, CategoryRequestDto dto);

    List<Category> getAllCategories(Long userId);

    String deleteCategory(Long id, Long userId);

    String updateCategory(Long id, Long userId, CategoryRequestDto dto);
}
