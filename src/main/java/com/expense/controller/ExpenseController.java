package com.expense.controller;

import com.expense.dtos.ExpenseRequest;
import com.expense.dtos.ExpenseResponseDto;
import com.expense.entity.Expense;
import com.expense.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    //add expense
    @PostMapping("/addExpense")
    public ResponseEntity<String> addExpense(@RequestBody ExpenseRequest req){
        String res = expenseService.addExpense(req);
        return new ResponseEntity<>(res,HttpStatus.CREATED);
    }

    //get all expense
    @GetMapping("/getAllExpenses")
    public ResponseEntity<List<ExpenseResponseDto>> getAllExpenses(@RequestParam Long userId){
        List<ExpenseResponseDto> expenses = expenseService.getAllExpenses(userId);
        return new ResponseEntity<>(expenses,HttpStatus.OK);
    }

    //get expense by date
    @GetMapping("/getExpenseBy-Date")
    public ResponseEntity<List<ExpenseResponseDto>> getExpenseByDate
    (@RequestParam Long userId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){

        List<ExpenseResponseDto> expenses = expenseService.getExpenseByDate(userId,date);
        return new ResponseEntity<>(expenses,HttpStatus.OK);
    }

    //get expense by category
    @GetMapping("/getExpenseBy-Category")
    public ResponseEntity<List<ExpenseResponseDto>> getByCategory(
            @RequestParam Long userId,
            @RequestParam String category) {
        List<ExpenseResponseDto> expenses = expenseService.getExpensesByCategory(userId, category);
        return new ResponseEntity<>(expenses,HttpStatus.OK);
    }

    //expense by month
    @GetMapping("/getExpenseBy-Month")
    public ResponseEntity<List<ExpenseResponseDto>> getByMonth(@RequestParam Long userId, @RequestParam int month, @RequestParam int year) {
        List<ExpenseResponseDto> expenses = expenseService.getExpenseByMonth(userId,month,year);
        return new ResponseEntity<>(expenses,HttpStatus.OK);
    }

    //today's expense
    @GetMapping("/today's-Expense")
    public ResponseEntity<List<ExpenseResponseDto>> getToday(@RequestParam Long userId) {
        List<ExpenseResponseDto> expenses=  expenseService.getTodaysExpenses(userId);
        return new ResponseEntity<>(expenses,HttpStatus.OK);
    }

    //monthly total
    @GetMapping("/getMonthly-Expensetotal")
    public ResponseEntity<Double> getMonthlyTotal(@RequestParam Long userId) {
        Double total =  expenseService.getMonthlyTotal(userId);
        return new ResponseEntity<>(total,HttpStatus.OK);
    }

    //get recent expenses/transactions
    @GetMapping("/recentExpense")
    public ResponseEntity<List<ExpenseResponseDto>> getRecentExpenses(@RequestParam Long userId,@RequestParam(defaultValue = "10") int limit) {
        List<ExpenseResponseDto> expenses = expenseService.getRecentExpenses(userId, limit);
        return new ResponseEntity<>(expenses,HttpStatus.OK);
    }
}
