package com.expense.dtos.budget_savings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SavingGoalRequestDto {

    private Long userId;
    private String goalName;
    private Double targetAmount;
    private Integer month;
    private Integer year;
}
