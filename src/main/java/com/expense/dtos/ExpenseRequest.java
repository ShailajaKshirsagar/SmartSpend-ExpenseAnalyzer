package com.expense.dtos;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpenseRequest {
    private String title;
    private Double amount;
    private String category;
    private Long userId;  // sent from frontend
}
