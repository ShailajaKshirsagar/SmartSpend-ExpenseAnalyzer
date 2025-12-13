package com.expense.dtos.insights;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class RecentTransactionDto {

    private String title;
    private LocalDate date;
    private Double amount;

}
