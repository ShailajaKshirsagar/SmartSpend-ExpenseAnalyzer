package com.expense.service;

import com.expense.dtos.budget_savings.BudgetDTO;
import org.jspecify.annotations.Nullable;

import java.time.LocalDate;

public interface MonthlyBudgetService {
    BudgetDTO setMonthlyBudget(Long userId, Double amount);

    void updateSpentAmount(Long userId, Double amount, LocalDate date);

     BudgetDTO getMonthlyBudget(Long userId);
}
