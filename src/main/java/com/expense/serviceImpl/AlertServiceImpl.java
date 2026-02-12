package com.expense.serviceImpl;

import com.expense.dtos.AlertResponseDto;
import com.expense.entity.Alert;
import com.expense.entity.CategoryBudget;
import com.expense.entity.MonthlyBudget;
import com.expense.entity.User;
import com.expense.exception.UserNotFoundException;
import com.expense.repository.*;
import com.expense.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlertServiceImpl implements AlertService{

    @Autowired
    UserRepository userRepository;
    @Autowired
    CategoryBudgetRepo categoryBudgetRepo;
    @Autowired
    ExpenseRepo expenseRepo;
    @Autowired
    IncomeRepo incomeRepo;
    @Autowired
    AlertRepository alertRepo;
    @Autowired
    MonthlyBudgetRepo monthlyBudgetRepo;
    @Override
    public void checkCategoryOverspend(Long userId, Long categoryId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        int month = LocalDate.now().getMonthValue();
        int year = LocalDate.now().getYear();
        CategoryBudget budget = categoryBudgetRepo
                .findByUserIdAndCategoryIdAndMonthAndYear(
                        userId,
                        categoryId,
                        month,
                        year)
                .orElse(null);
        if (budget == null) return;
        double spent = expenseRepo.getCategoryMonthlyTotal(
                userId,
                budget.getCategory().getName(),
                month,
                year);
        if (spent > budget.getLimitAmount()) {
            double overSpent = spent - budget.getLimitAmount();
            boolean alreadyExists =
                    alertRepo.findByUserIdAndCategoryIdAndTypeAndMonthAndYear(
                            userId,
                            categoryId,
                            "CATEGORY OVERSPEND",
                            month,
                            year
                    ).isPresent();
            if (!alreadyExists) {
                Alert alert = Alert.builder()
                        .message(
                                "You have exceeded your monthly budget for category: "
                                        + budget.getCategory().getName()
                                        + " by ₹" + String.format("%.2f", overSpent))
                        .type("CATEGORY OVERSPEND")
                        .readStatus(false)
                        .month(month)
                        .year(year)
                        .createdAt(LocalDateTime.now())
                        .user(user)
                        .category(budget.getCategory())
                        .build();

                alertRepo.save(alert);
            }
        }
    }
    @Override
    public void checkIncomeVsExpense(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        int month = LocalDate.now().getMonthValue();
        int year = LocalDate.now().getYear();

        double income = incomeRepo.getMonthlyTotal(userId, month, year);
        double expense = expenseRepo.getMonthlyTotal(userId, month, year);
        if (income == 0) return;

        if (expense > income) {
            boolean alreadyExists =
                    alertRepo.findByUserIdAndTypeAndMonthAndYear(
                            userId,
                            "INCOME EXPENSE IMBALANCE",
                            month,
                            year
                    ).isPresent();
            if (!alreadyExists) {

                Alert alert = Alert.builder()
                        .message("Your expenses are higher than your income this month!!")
                        .type("INCOME EXPENSE IMBALANCE")
                        .readStatus(false)
                        .month(month)
                        .year(year)
                        .createdAt(LocalDateTime.now())
                        .user(user)
                        .category(null)
                        .build();

                alertRepo.save(alert);
            }
        }
    }

    @Override
    public List<AlertResponseDto> getUnreadAlerts(Long userId) {
        return alertRepo.findAll()
                .stream()
                .filter(a -> a.getUser().getId().equals(userId) && !a.isReadStatus())
                .map(a -> AlertResponseDto.builder()
                        .alertId(a.getId())
                        .message(a.getMessage())
                        .type(a.getType())
                        .read(a.isReadStatus())
                        .build())
                .toList();
    }

    @Override
    public void markAlertAsRead(Long alertId, Long userId) {
        Alert alert = alertRepo.findById(alertId)
                .orElseThrow(() -> new RuntimeException("Alert not found"));

        if (!alert.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }
        alert.setReadStatus(true);
        alertRepo.save(alert);
    }

    //for monthly budget
    @Override
    public void checkMonthlyBudgetOverspend(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        int month = LocalDate.now().getMonthValue();
        int year = LocalDate.now().getYear();
        MonthlyBudget monthlyBudget = monthlyBudgetRepo
                .findByUserIdAndMonthAndYear(userId, month, year)
                .orElse(null);
        if (monthlyBudget == null) return;
        double remainingAmount = monthlyBudget.getRemainingAmount();

        if (remainingAmount < 0) {
            double overSpent = Math.abs(remainingAmount);
            boolean alreadyExists =
                    alertRepo.findByUserIdAndTypeAndMonthAndYear(
                            userId,
                            "MONTHLY BUDGET OVERSPEND",
                            month,
                            year
                    ).isPresent();
            if (!alreadyExists) {
                Alert alert = Alert.builder()
                        .message(
                                "You have exceeded your monthly budget by ₹"
                                        + String.format("%.2f", overSpent))
                        .type("MONTHLY BUDGET OVERSPEND")
                        .readStatus(false)
                        .month(month)
                        .year(year)
                        .createdAt(LocalDateTime.now())
                        .user(user)
                        .category(null)
                        .build();
                alertRepo.save(alert);
            }
        }
    }
}