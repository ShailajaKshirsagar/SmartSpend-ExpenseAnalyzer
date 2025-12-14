package com.expense.service;

import com.expense.dtos.budget_savings.*;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface SavingService {
    String addSaving(SavingRequestDto dto);

    @Nullable SavingGoalResponseDto createGoal(SavingGoalRequestDto dto);

    MonthlySavingResponseDto getThisMonthSavings(Long userId);

    MonthlySavingResponseDto getPastMonthSavings(Long userId);

    //suggestions
    List<SmartSuggestionDto> getSuggestions(Long userId);
}
