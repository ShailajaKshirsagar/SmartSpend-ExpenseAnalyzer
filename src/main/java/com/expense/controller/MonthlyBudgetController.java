package com.expense.controller;

import com.expense.dtos.budget_savings.BudgetDTO;
import com.expense.service.MonthlyBudgetService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/budget")
public class MonthlyBudgetController {

    @Autowired
    MonthlyBudgetService monthlyBudgetService;

    //set budget
    @PostMapping("/set-budget")
    public ResponseEntity<BudgetDTO> setBudget(@RequestParam Double amount, Authentication authentication){
        Long userId = (Long) authentication.getPrincipal();
        BudgetDTO budget = monthlyBudgetService.setMonthlyBudget(userId,amount);
        return new ResponseEntity<>(budget, HttpStatus.CREATED);
    }

    //get monthly budget
    @GetMapping("/get-current-budget")
    public ResponseEntity<BudgetDTO> getCurrentBudget(Authentication authentication){
        Long userId = (Long) authentication.getPrincipal();
        return ResponseEntity.ok(monthlyBudgetService.getMonthlyBudget(userId));
    }
}
