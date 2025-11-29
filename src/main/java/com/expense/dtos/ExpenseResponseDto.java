package com.expense.dtos;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseResponseDto
{
    private String title;

    private Double amount;

    private String category;

    private LocalDate date;
}
