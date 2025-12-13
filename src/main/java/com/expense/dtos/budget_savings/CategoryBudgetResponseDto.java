package com.expense.dtos.budget_savings;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryBudgetResponseDto {
    private Long id;
    private Long categoryId;
    private String categoryName;
    private Double limitAmount;
    private Double spentAmount;
    private Double remainingAmount;
    private Integer month;
    private Integer year;
}
