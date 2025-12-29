package com.expense.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpenseRequest {
    @NotBlank(message = "Title or Name of Expense is required")
    private String title;
    private Double amount;
    private String category;
    private Long userId;
}
