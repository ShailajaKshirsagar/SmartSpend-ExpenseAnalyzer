package com.expense.controller;

import com.expense.dtos.budget_savings.*;
import com.expense.service.SavingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/savings")
public class SavingController {

    @Autowired
    SavingService savingService;

    @PostMapping("/set-goal")
    public ResponseEntity<SavingGoalResponseDto> createGoal(
            @RequestBody SavingGoalRequestDto dto,Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        return ResponseEntity.ok(savingService.createGoal(dto,userId));
    }

    @PostMapping("/add-savings")
    public ResponseEntity<String> addSaving(@RequestBody SavingRequestDto dto,Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        String msg = savingService.addSaving(dto,userId);
        return new ResponseEntity<>(msg, HttpStatus.CREATED);
    }

    @GetMapping("/this-month-savings")
    public MonthlySavingResponseDto thisMonthSavings(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        return savingService.getThisMonthSavings(userId);
    }

    @GetMapping("/past-month-savings")
    public MonthlySavingResponseDto pastMonthSavings(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        return savingService.getPastMonthSavings(userId);
    }

    //suggestions
    @GetMapping("/get-suggestions")
    public ResponseEntity<List<SmartSuggestionDto>> getSuggestions(Authentication authentication){
        Long userId = (Long) authentication.getPrincipal();
        return new ResponseEntity<>(savingService.getSuggestions(userId),HttpStatus.OK);
    }
}
