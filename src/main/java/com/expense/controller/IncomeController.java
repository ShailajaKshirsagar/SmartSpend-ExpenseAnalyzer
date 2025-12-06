package com.expense.controller;

import com.expense.dtos.IncomeRequestDto;
import com.expense.dtos.IncomeResponseDto;
import com.expense.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/income")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    //add income
    @PostMapping("/add-income")
    public ResponseEntity<String> createIncome(@RequestParam Long userId, @RequestBody IncomeRequestDto dto) {
        String msg = incomeService.createIncome(userId, dto);
        return new ResponseEntity<>(msg, HttpStatus.CREATED);
    }

    //get income
    @GetMapping("/get-income")
    public ResponseEntity<List<IncomeResponseDto>> getIncome(@RequestParam Long userId) {
        List<IncomeResponseDto> income = incomeService.getIncome(userId);
        return new ResponseEntity<>(income,HttpStatus.OK);
    }

    //get monthly income summary api
    @GetMapping("/get-monthly-incometotal")
    public ResponseEntity<Double> getMonthlyTotal(@RequestParam Long userId){
        Double total = incomeService.getMonthlyTotal(userId);
        return new ResponseEntity<>(total,HttpStatus.OK);
    }
}
