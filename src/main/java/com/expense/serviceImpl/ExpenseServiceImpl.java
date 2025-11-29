package com.expense.serviceImpl;

import com.expense.dtos.ExpenseRequest;
import com.expense.dtos.ExpenseResponseDto;
import com.expense.entity.Expense;
import com.expense.entity.User;
import com.expense.repository.ExpenseRepo;
import com.expense.repository.UserRepository;
import com.expense.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService
{
    @Autowired
    private ExpenseRepo expenseRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public String addExpense(ExpenseRequest req) {
        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Expense exp = Expense.builder()
                .title(req.getTitle())
                .amount(req.getAmount())
                .date(LocalDate.now())
                .category(req.getCategory().toLowerCase())
                .user(user)
                .build();
        expenseRepository.save(exp);
        return "Expense Added Successfully";
    }

    @Override
    public List<ExpenseResponseDto> getAllExpenses(Long userId) {
        List<Expense> expenseList = expenseRepository.findByUserId(userId);
        return expenseList.stream()
                .map(expense -> new ExpenseResponseDto(
                        expense.getTitle(),
                        expense.getAmount(),
                        expense.getCategory(),
                        expense.getDate()
                ))
                .toList();
    }

    @Override
    public List<ExpenseResponseDto> getExpenseByDate(Long userId, LocalDate date) {
        List<Expense> expenses = expenseRepository.findByUserIdAndDate(userId,date);
        return  expenses.stream()
                .map(expense -> new ExpenseResponseDto(
                        expense.getTitle(),
                        expense.getAmount(),
                        expense.getCategory(),
                        expense.getDate()
                ))
                .toList();
    }

    @Override
    public List<ExpenseResponseDto> getExpensesByCategory(Long userId, String category) {
        List<Expense> expenses = expenseRepository.findByCategory(userId,category);
        return  expenses.stream()
                .map(expense -> new ExpenseResponseDto(
                        expense.getTitle(),
                        expense.getAmount(),
                        expense.getCategory(),
                        expense.getDate()
                ))
                .toList();
    }

    @Override
    public List<ExpenseResponseDto> getExpenseByMonth(Long userId, int month, int year) {
        List<Expense> expenses = expenseRepository.findByMonth(userId,month,year);
        return  expenses.stream()
                .map(expense -> new ExpenseResponseDto(
                        expense.getTitle(),
                        expense.getAmount(),
                        expense.getCategory(),
                        expense.getDate()
                ))
                .toList();
    }

    @Override
    public List<ExpenseResponseDto> getTodaysExpenses(Long userId) {
            LocalDate today = LocalDate.now();
            List<Expense> expenses= expenseRepository.getTodaysExpenses(userId, today);
        return  expenses.stream()
                .map(expense -> new ExpenseResponseDto(
                        expense.getTitle(),
                        expense.getAmount(),
                        expense.getCategory(),
                        expense.getDate()
                ))
                .toList();
    }

    @Override
    public Double getMonthlyTotal(Long userId) {
        LocalDate today = LocalDate.now();
        int month = today.getMonthValue();
        int year = today.getYear();
        return expenseRepository.getMonthlyTotal(userId, month, year);
    }

    @Override
    public List<ExpenseResponseDto> getRecentExpenses(Long userId, int limit) {
        if (limit <= 0) limit = 10;
        List<Expense> expenses = expenseRepository.findRecentExpenses(userId, PageRequest.of(0, limit));
        return  expenses.stream()
                .map(expense -> new ExpenseResponseDto(
                        expense.getTitle(),
                        expense.getAmount(),
                        expense.getCategory(),
                        expense.getDate()
                ))
                .toList();
    }


}
