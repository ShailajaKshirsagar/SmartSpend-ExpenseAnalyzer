package com.expense.dtos.otp;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class BillResponseDto {
    private String billName;
    private Double amount;
    private LocalDate dueDate;
    private Boolean isRecurring;
    private Boolean isPaid;
}
