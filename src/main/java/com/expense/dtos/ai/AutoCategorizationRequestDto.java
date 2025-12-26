package com.expense.dtos.ai;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AutoCategorizationRequestDto {
    private String notificationText;
}
