package com.expense.service;

import com.expense.dtos.AlertResponseDto;

import java.util.List;

public interface AlertService {

    void checkCategoryOverspend(Long userId, Long categoryId);

    void checkIncomeVsExpense(Long userId);

    List<AlertResponseDto> getUnreadAlerts(Long userId);

    void markAlertAsRead(Long alertId, Long userId);

    //
    void checkMonthlyBudgetOverspend(Long userId);
}
