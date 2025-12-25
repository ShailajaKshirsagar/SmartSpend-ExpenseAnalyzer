package com.expense.controller;

import com.expense.dtos.budget_savings.CategoryBudgetRequest;
import com.expense.dtos.budget_savings.CategoryBudgetResponseDto;
import com.expense.service.CategoryBudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category-budget")
public class CategoryBudgetController {

    @Autowired
    CategoryBudgetService categoryBudgetService;

    @PostMapping("/set-budget")
    public CategoryBudgetResponseDto setBudget(@RequestBody CategoryBudgetRequest req, Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        return categoryBudgetService.setCategoryBudget(req,userId);
    }

    @GetMapping("/get-budget")
    public List<CategoryBudgetResponseDto> getBudgets(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        return categoryBudgetService.getCategoryBudgets(userId);
    }
}
