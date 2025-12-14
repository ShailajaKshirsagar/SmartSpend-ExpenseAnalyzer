package com.expense.controller;

import com.expense.dtos.budget_savings.*;
import com.expense.service.SavingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/savings")
public class SavingController {

    @Autowired
    SavingService savingService;

    @PostMapping("/set-goal")
    public ResponseEntity<SavingGoalResponseDto> createGoal(
            @RequestBody SavingGoalRequestDto dto) {
        return ResponseEntity.ok(savingService.createGoal(dto));
    }

    @PostMapping("/add-savings")
    public ResponseEntity<String> addSaving(@RequestBody SavingRequestDto dto) {
        String msg = savingService.addSaving(dto);
        return new ResponseEntity<>(msg, HttpStatus.CREATED);
    }

    @GetMapping("/this-month-savings/{userId}")
    public MonthlySavingResponseDto thisMonthSavings(@PathVariable Long userId) {
        return savingService.getThisMonthSavings(userId);
    }

    @GetMapping("/past-month-savings/{userId}")
    public MonthlySavingResponseDto pastMonthSavings(@PathVariable Long userId) {
        return savingService.getPastMonthSavings(userId);
    }

    //suggestions
    @GetMapping("/get-suggestions")
    public ResponseEntity<List<SmartSuggestionDto>> getSuggestions(@RequestParam Long userId){
        return new ResponseEntity<>(savingService.getSuggestions(userId),HttpStatus.OK);
    }
}
