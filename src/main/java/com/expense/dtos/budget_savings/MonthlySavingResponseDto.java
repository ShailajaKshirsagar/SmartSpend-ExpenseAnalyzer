package com.expense.dtos.budget_savings;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MonthlySavingResponseDto {
    private Integer month;
    private Integer year;
    private Double totalSaved;
}
