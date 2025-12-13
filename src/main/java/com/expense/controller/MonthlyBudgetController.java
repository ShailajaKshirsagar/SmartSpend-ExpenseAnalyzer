package com.expense.controller;

import com.expense.dtos.budget_savings.BudgetDTO;
import com.expense.service.MonthlyBudgetService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/budget")
public class MonthlyBudgetController {

    @Autowired
    MonthlyBudgetService monthlyBudgetService;

    //set budget
    @PostMapping("/set-budget")
    public ResponseEntity<BudgetDTO> setBudget(@RequestParam Long userId,@RequestParam Double amount){
        BudgetDTO budget = monthlyBudgetService.setMonthlyBudget(userId,amount);
        return new ResponseEntity<>(budget, HttpStatus.CREATED);
    }

    //get monthly budget
    @GetMapping("/get-current-budget")
    public ResponseEntity<BudgetDTO> getCurrentBudget(@RequestParam Long userId){
        return ResponseEntity.ok(monthlyBudgetService.getMonthlyBudget(userId));
    }
}
