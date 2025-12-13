package com.expense.dtos.insights;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DashBoardResponseDto {

    private Double totalBalance;
    private Double thisMonthIncome;
    private Double thisMonthExpense;
    private List<RecentTransactionDto> recentTransactions;
}
