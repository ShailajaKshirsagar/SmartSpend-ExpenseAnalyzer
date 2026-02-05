package com.expense.serviceImpl;

import com.expense.dtos.ExpenseRequest;
import com.expense.dtos.ExpenseResponseDto;
import com.expense.entity.Expense;
import com.expense.entity.User;
import com.expense.exception.UserNotFoundException;
import com.expense.repository.CategoryRepo;
import com.expense.repository.ExpenseRepo;
import com.expense.repository.UserRepository;
import com.expense.service.AlertService;
import com.expense.service.ExpenseService;
import com.expense.service.MonthlyBudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.data.util.ClassUtils.ifPresent;

@Service
public class ExpenseServiceImpl implements ExpenseService
{
    @Autowired
    private ExpenseRepo expenseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    MonthlyBudgetService monthlyBudgetService;
    @Autowired
    AlertService alertService;
    @Autowired
    CategoryRepo categoryRepo;


    @Override
    public String addExpense(ExpenseRequest req,Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->  new UserNotFoundException("User Not Found"));
        Expense exp = Expense.builder()
                .title(req.getTitle())
                .amount(req.getAmount())
                .date(LocalDate.now())
                .category(req.getCategory().toLowerCase())
                .user(user)
                .build();
        Expense saved = expenseRepository.save(exp);
        categoryRepo
                .findByNameIgnoreCaseAndUserId(saved.getCategory(), userId)
                .ifPresent(category -> {
                    alertService.checkCategoryOverspend(userId, category.getId());
                });
        alertService.checkIncomeVsExpense(userId);
        //to update budget
        monthlyBudgetService.updateSpentAmount(req.getUserId(), saved.getAmount(), saved.getDate());
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
        Page<Expense> expenses = expenseRepository.findRecentExpenses(userId, PageRequest.of(0, limit));
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
