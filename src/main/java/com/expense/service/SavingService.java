package com.expense.service;

import com.expense.dtos.budget_savings.MonthlySavingResponseDto;
import com.expense.dtos.budget_savings.SavingGoalRequestDto;
import com.expense.dtos.budget_savings.SavingGoalResponseDto;
import com.expense.dtos.budget_savings.SavingRequestDto;
import org.jspecify.annotations.Nullable;

public interface SavingService {
    String addSaving(SavingRequestDto dto);

    @Nullable SavingGoalResponseDto createGoal(SavingGoalRequestDto dto);

    MonthlySavingResponseDto getThisMonthSavings(Long userId);

    MonthlySavingResponseDto getPastMonthSavings(Long userId);
}
