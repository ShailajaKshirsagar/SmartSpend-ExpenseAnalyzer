package com.expense.dtos.insights;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategorySummaryDto {

    private String categoryName;
    private Double totalSpent;
}
