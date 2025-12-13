package com.expense.dtos.budget_savings;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryBudgetRequest {
    private Long userId;
    private Long categoryId;
    private Double limitAmount;
}
