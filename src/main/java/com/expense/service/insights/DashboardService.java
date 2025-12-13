package com.expense.service.insights;

import com.expense.dtos.insights.DashBoardResponseDto;

public interface DashboardService {
    DashBoardResponseDto getDashboard(Long userId);
}
