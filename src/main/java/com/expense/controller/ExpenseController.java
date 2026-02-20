package com.expense.controller;

import com.expense.dtos.ExpenseRequest;
import com.expense.dtos.ExpenseResponseDto;
import com.expense.entity.Expense;
import com.expense.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    //add expense
    @PostMapping("/add-expense")
    public ResponseEntity<String> addExpense(@RequestBody ExpenseRequest req,Authentication authentication){
        Long userId = (Long) authentication.getPrincipal();
        System.out.println("ADD userId: " + userId);
        String res = expenseService.addExpense(req,userId);
        return new ResponseEntity<>(res,HttpStatus.CREATED);
    }

    //get all expense
    @GetMapping("/get-all-expenses")
    public ResponseEntity<List<ExpenseResponseDto>> getAllExpenses(Authentication authentication){
        Long userId = (Long) authentication.getPrincipal();
        System.out.println("GET userId: " + userId);
        List<ExpenseResponseDto> expenses = expenseService.getAllExpenses(userId);
        return new ResponseEntity<>(expenses,HttpStatus.OK);
    }

    //get expense by date
    @GetMapping("/getexpenseby-date")
    public ResponseEntity<List<ExpenseResponseDto>> getExpenseByDate
    ( @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,Authentication authentication){
        Long userId = (Long) authentication.getPrincipal();
        List<ExpenseResponseDto> expenses = expenseService.getExpenseByDate(userId,date);
        return new ResponseEntity<>(expenses,HttpStatus.OK);
    }

    //get expense by category
    @GetMapping("/getexpenseby-category")
    public ResponseEntity<List<ExpenseResponseDto>> getByCategory(
            @RequestParam String category,Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        List<ExpenseResponseDto> expenses = expenseService.getExpensesByCategory(userId, category);
        return new ResponseEntity<>(expenses,HttpStatus.OK);
    }

    //expense by month
    @GetMapping("/get-expenseby-month")
    public ResponseEntity<List<ExpenseResponseDto>> getByMonth(@RequestParam int month, @RequestParam int year,Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        List<ExpenseResponseDto> expenses = expenseService.getExpenseByMonth(userId,month,year);
        return new ResponseEntity<>(expenses,HttpStatus.OK);
    }

    //today's expense
    @GetMapping("/today's-expense")
    public ResponseEntity<List<ExpenseResponseDto>> getToday(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        List<ExpenseResponseDto> expenses=  expenseService.getTodaysExpenses(userId);
        return new ResponseEntity<>(expenses,HttpStatus.OK);
    }

    //monthly total
    @GetMapping("/get-monthly-expensetotal")
    public ResponseEntity<Double> getMonthlyTotal(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        Double total =  expenseService.getMonthlyTotal(userId);
        return new ResponseEntity<>(total,HttpStatus.OK);
    }

    //get recent expenses/transactions
    @GetMapping("/recent-expense")
    public ResponseEntity<List<ExpenseResponseDto>> getRecentExpenses(@RequestParam(defaultValue = "10") int limit,Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        List<ExpenseResponseDto> expenses = expenseService.getRecentExpenses(userId, limit);
        return new ResponseEntity<>(expenses,HttpStatus.OK);
    }
}
