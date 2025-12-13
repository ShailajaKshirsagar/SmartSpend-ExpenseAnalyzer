package com.expense.dtos.budget_savings;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavingRequestDto {
    private Long userId;
    private Long goalId;
    private Double amount;
}

