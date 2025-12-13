package com.expense.service;

import com.expense.dtos.budget_savings.CategoryBudgetRequest;
import com.expense.dtos.budget_savings.CategoryBudgetResponseDto;

import java.util.List;

public interface CategoryBudgetService {

    CategoryBudgetResponseDto setCategoryBudget(CategoryBudgetRequest req);
    void deductExpense(Long userId, Long categoryId, Double amount);

    List<CategoryBudgetResponseDto> getCategoryBudgets(Long userId);
}
