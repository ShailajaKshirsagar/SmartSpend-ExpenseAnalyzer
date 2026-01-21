package com.expense.service;

import com.expense.dtos.budget_savings.*;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatusCode;

import java.util.List;

public interface SavingService {
    String addSaving(SavingRequestDto dto, Long userId);

    @Nullable SavingGoalResponseDto createGoal(SavingGoalRequestDto dto, Long userId);

    MonthlySavingResponseDto getThisMonthSavings(Long userId);

    MonthlySavingResponseDto getPastMonthSavings(Long userId);

    //suggestions
    List<SmartSuggestionDto> getSuggestions(Long userId);

}
