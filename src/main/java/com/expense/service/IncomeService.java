package com.expense.service;

import com.expense.dtos.IncomeRequestDto;
import com.expense.dtos.IncomeResponseDto;

import java.util.List;

public interface IncomeService {
    String createIncome(Long userId, IncomeRequestDto dto);

    List<IncomeResponseDto> getIncome(Long userId);

    Double getMonthlyTotal(Long userId);
}
