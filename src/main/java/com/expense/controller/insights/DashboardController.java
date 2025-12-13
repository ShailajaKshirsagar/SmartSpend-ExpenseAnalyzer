package com.expense.controller.insights;

import com.expense.dtos.insights.DashBoardResponseDto;
import com.expense.service.insights.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/get-dashboard")
    public ResponseEntity<DashBoardResponseDto> getDashboard(@RequestParam Long userId) {
        DashBoardResponseDto dto = dashboardService.getDashboard(userId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}

