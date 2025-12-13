package com.expense.controller;

import com.expense.dtos.budget_savings.CategoryBudgetRequest;
import com.expense.dtos.budget_savings.CategoryBudgetResponseDto;
import com.expense.service.CategoryBudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category-budget")
public class CategoryBudgetController {

    @Autowired
    CategoryBudgetService categoryBudgetService;

    @PostMapping("/set-budget")
    public CategoryBudgetResponseDto setBudget(@RequestBody CategoryBudgetRequest req) {
        return categoryBudgetService.setCategoryBudget(req);
    }

    @GetMapping("/get-budget/{userId}")
    public List<CategoryBudgetResponseDto> getBudgets(@PathVariable Long userId) {
        return categoryBudgetService.getCategoryBudgets(userId);
    }
}
