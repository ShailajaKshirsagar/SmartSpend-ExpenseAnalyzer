package com.expense.serviceImpl;

import com.expense.dtos.CategoryRequestDto;
import com.expense.entity.Category;
import com.expense.entity.User;
import com.expense.exception.CategoryNotFound;
import com.expense.exception.UserNotFoundException;
import com.expense.repository.CategoryRepo;
import com.expense.repository.UserRepository;
import com.expense.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public String saveCategory(Long userId, CategoryRequestDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->  new UserNotFoundException("User Not Found"));
        Category category = Category.builder()
                .createdAt(LocalDate.now())
                .name(dto.getName())
                .type(dto.getType())
                .user(user).build();
        categoryRepository.save(category);
        return "Category saved";
    }

    @Override
    public List<Category> getAllCategories(Long userId) {
        List<Category> categoryList = categoryRepository.findByUserId(userId);
        return categoryList;
    }

    @Override
    public String deleteCategory(Long id, Long userId) {
        Category category = categoryRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new CategoryNotFound("Category not found"));
        categoryRepository.delete(category);
        return "Category deleted";
    }

    @Override
    public String updateCategory(Long id, Long userId, CategoryRequestDto dto) {
        Category category = categoryRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new CategoryNotFound("Category not found"));
        category.setName(dto.getName());
        category.setType(dto.getType());
        categoryRepository.save(category);
        return "Category Updated";
    }
}
