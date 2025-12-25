package com.expense.service;

import com.expense.dtos.ExpenseRequest;
import com.expense.dtos.ExpenseResponseDto;
import com.expense.entity.Expense;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseService {
    String addExpense(ExpenseRequest req,Long userId);

    List<ExpenseResponseDto> getAllExpenses(Long userId);

    List<ExpenseResponseDto> getExpenseByDate(Long userId, LocalDate date);

    List<ExpenseResponseDto> getExpensesByCategory(Long userId, String category);

    List<ExpenseResponseDto> getExpenseByMonth(Long userId, int month, int year);

    List<ExpenseResponseDto> getTodaysExpenses(Long userId);

    Double getMonthlyTotal(Long userId);

    List<ExpenseResponseDto> getRecentExpenses(Long userId, int limit);

}
