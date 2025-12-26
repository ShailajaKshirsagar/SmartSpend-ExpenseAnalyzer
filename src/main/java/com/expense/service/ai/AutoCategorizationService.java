package com.expense.service.ai;

import com.expense.dtos.ai.AutoCategorizationResponseDto;

public interface AutoCategorizationService {
    AutoCategorizationResponseDto process(String notificationText, Long userId);

}
