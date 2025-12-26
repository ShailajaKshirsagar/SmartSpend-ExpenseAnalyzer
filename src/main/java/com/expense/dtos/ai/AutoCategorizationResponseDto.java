package com.expense.dtos.ai;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AutoCategorizationResponseDto {
    private String status;
    private String message;
}
