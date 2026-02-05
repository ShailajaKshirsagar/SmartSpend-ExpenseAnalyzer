package com.expense.controller;
import com.expense.dtos.AlertResponseDto;
import com.expense.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class AlertsController {

    @Autowired
    private AlertService alertService;

    @GetMapping("/get-unread-alerts")
    public ResponseEntity<List<AlertResponseDto>> getUnreadAlerts(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        List<AlertResponseDto> alerts = alertService.getUnreadAlerts(userId);
        return ResponseEntity.ok(alerts);
    }
}
