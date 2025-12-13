package com.expense.dtos.budget_savings;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SavingGoalResponseDto {
    private Long id;
    private Long userId;
    private String title;
    private Double targetAmount;
    private Double savedAmount;
    private Double remainingAmount;
}
