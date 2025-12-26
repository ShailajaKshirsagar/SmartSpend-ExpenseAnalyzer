package com.expense.controller.ai;

import com.expense.dtos.ai.AutoCategorizationRequestDto;
import com.expense.service.ai.AutoCategorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
public class AutoCategorizationController {

    @Autowired
    private AutoCategorizationService autoCategorizationService;

    @PostMapping("/auto-categorize")
    public ResponseEntity<?> autoCategorize(@RequestBody AutoCategorizationRequestDto dto, Authentication authentication){
        Long userId = (Long) authentication.getPrincipal();
        return ResponseEntity.ok(
                autoCategorizationService.process(dto.getNotificationText(),userId)
        );
    }
}
