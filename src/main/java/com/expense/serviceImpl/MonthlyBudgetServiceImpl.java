package com.expense.serviceImpl;

import com.expense.dtos.budget_savings.BudgetDTO;
import com.expense.entity.MonthlyBudget;
import com.expense.entity.User;
import com.expense.repository.MonthlyBudgetRepo;
import com.expense.repository.UserRepository;
import com.expense.service.MonthlyBudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MonthlyBudgetServiceImpl implements MonthlyBudgetService {

    @Autowired
    MonthlyBudgetRepo monthlyBudgetRepository;

    @Autowired
    UserRepository userRepository;

    //validate the user
    public User validateUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }

    @Override
    public BudgetDTO setMonthlyBudget(Long userId, Double amount) {
        User user = validateUser(userId);
        LocalDate now = LocalDate.now();
        MonthlyBudget budget = monthlyBudgetRepository.findByUserIdAndMonthAndYear(userId, now.getMonthValue(), now.getYear())
                .orElse(MonthlyBudget.builder()
                        .userId(userId)
                        .spentAmount(0.0)
                        .month(now.getMonthValue())
                        .year(now.getYear())
                        .build()
                );
        budget.setTotalBudget(amount);
        budget.setRemainingAmount(amount- budget.getSpentAmount());
        monthlyBudgetRepository.save(budget);

        return mapToDTO(budget);
    }

    //when user add expense budget should be updated
    @Override
    public void updateSpentAmount(Long userId, Double amount, LocalDate date) {
        MonthlyBudget budget = monthlyBudgetRepository
                .findByUserIdAndMonthAndYear(userId, date.getMonthValue(), date.getYear())
                .orElseThrow(() -> new RuntimeException("Budget not set for this month"));
        //update spent and remaining amount
        budget.setSpentAmount(budget.getSpentAmount() + amount);
        budget.setRemainingAmount(budget.getTotalBudget() - budget.getSpentAmount());
        monthlyBudgetRepository.save(budget);
    }

    @Override
    public BudgetDTO getMonthlyBudget(Long userId) {
        LocalDate now = LocalDate.now();
        return monthlyBudgetRepository.findByUserIdAndMonthAndYear(userId, now.getMonthValue(),now.getYear())
                .map(this::mapToDTO)
                .orElse(null);
    }
    //for dto mapping
    private BudgetDTO mapToDTO(MonthlyBudget b) {
        return BudgetDTO.builder()
                .totalBudget(b.getTotalBudget())
                .spentAmount(b.getSpentAmount())
                .remainingAmount(b.getRemainingAmount())
                .month(b.getMonth())
                .year(b.getYear())
                .build();
    }
}
