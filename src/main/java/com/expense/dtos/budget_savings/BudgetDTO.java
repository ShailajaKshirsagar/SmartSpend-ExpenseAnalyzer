package com.expense.dtos.budget_savings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BudgetDTO {
    private Double totalBudget;
    private Double spentAmount;
    private Double remainingAmount;
    private Integer month;
    private Integer year;
}
