package com.expense.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Long userId;
}
