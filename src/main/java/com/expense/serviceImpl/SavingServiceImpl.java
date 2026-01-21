package com.expense.serviceImpl;

import com.expense.dtos.budget_savings.*;
import com.expense.entity.SavingGoal;
import com.expense.entity.SavingTransaction;
import com.expense.entity.User;
import com.expense.exception.SavingGoalNotFound;
import com.expense.exception.UserNotFoundException;
import com.expense.repository.*;
import com.expense.service.SavingService;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
public class SavingServiceImpl implements SavingService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    SavingGoalRepo savingGoalRepo;
    @Autowired
    SavingTransactionRepo savingTransactionRepo;
    @Autowired
    IncomeRepo incomeRepo;
    @Autowired
    ExpenseRepo expenseRepo;
    @Autowired
    MonthlyBudgetRepo monthlyBudgetRepo;

    @Override
    public @Nullable SavingGoalResponseDto createGoal(SavingGoalRequestDto dto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->  new UserNotFoundException("User Not Found"));
        SavingGoal goal = SavingGoal.builder()
                .title(dto.getGoalName())
                .targetAmount(dto.getTargetAmount())
                .savedAmount(0.0)
                .user(user)
                .build();

        SavingGoal savedGoal = savingGoalRepo.save(goal);

        return SavingGoalResponseDto.builder()
                .id(savedGoal.getId())
                .title(savedGoal.getTitle())
                .targetAmount(savedGoal.getTargetAmount())
                .savedAmount(savedGoal.getSavedAmount())
                .remainingAmount(
                        savedGoal.getTargetAmount() - savedGoal.getSavedAmount()
                )
                .build();
    }


    @Override
    public String addSaving(SavingRequestDto dto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->  new UserNotFoundException("User Not Found"));

        SavingGoal goal = null;

    if(dto.getGoalId()!=null){
        goal = savingGoalRepo.findById(dto.getGoalId())
                .orElseThrow(() -> new SavingGoalNotFound("Saving goal not found"));

        goal.setSavedAmount(goal.getSavedAmount() + dto.getAmount());
        savingGoalRepo.save(goal);
    }
    SavingTransaction txn = SavingTransaction.builder()
            .amount(dto.getAmount())
            .date(LocalDate.now())
            .user(user)
            .savingGoal(goal)
            .build();

    savingTransactionRepo.save(txn);
    return "Savings Added";
}

    @Override
    public MonthlySavingResponseDto getThisMonthSavings(Long userId) {
        return calculateMonthlySavings(userId, YearMonth.now());
    }

    @Override
    public MonthlySavingResponseDto getPastMonthSavings(Long userId) {
        YearMonth previousMonth = YearMonth.now().minusMonths(1);
        return calculateMonthlySavings(userId, previousMonth);
    }

    private MonthlySavingResponseDto calculateMonthlySavings(Long userId, YearMonth ym) {

        LocalDate start = ym.atDay(1);
        LocalDate end = ym.atEndOfMonth();

        double total = savingTransactionRepo
                .findByUserIdAndDateBetween(userId, start, end)
                .stream()
                .mapToDouble(SavingTransaction::getAmount)
                .sum();

        return MonthlySavingResponseDto.builder()
                .month(ym.getMonthValue())
                .year(ym.getYear())
                .totalSaved(total)
                .build();
    }

    //suggestions
    @Override
    public List<SmartSuggestionDto> getSuggestions(Long userId) {

        List<SmartSuggestionDto> suggestions = new ArrayList<>();
        int month = LocalDate.now().getMonthValue();
        int year = LocalDate.now().getYear();
        double income =
                incomeRepo.getMonthlyTotal(userId, month, year);
        double expense =
                expenseRepo.getMonthlyTotal(userId, month, year);
        if (income > 0 && expense < income * 0.6) {
            suggestions.add(
                    new SmartSuggestionDto("You are doing great! You can save more this month!!"));
        }
        if (expense > income * 0.9) {
            suggestions.add(
                    new SmartSuggestionDto("Your expenses are high this month. Try reducing non-essential spending."));
        }
        return suggestions;
    }

}
