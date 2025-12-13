package com.expense.serviceImpl;

import com.expense.dtos.budget_savings.MonthlySavingResponseDto;
import com.expense.dtos.budget_savings.SavingGoalRequestDto;
import com.expense.dtos.budget_savings.SavingGoalResponseDto;
import com.expense.dtos.budget_savings.SavingRequestDto;
import com.expense.entity.SavingGoal;
import com.expense.entity.SavingTransaction;
import com.expense.entity.User;
import com.expense.repository.SavingGoalRepo;
import com.expense.repository.SavingTransactionRepo;
import com.expense.repository.UserRepository;
import com.expense.service.SavingService;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;

@Service
public class SavingServiceImpl implements SavingService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    SavingGoalRepo savingGoalRepo;
    @Autowired
    SavingTransactionRepo savingTransactionRepo;

    @Override
    public @Nullable SavingGoalResponseDto createGoal(SavingGoalRequestDto dto) {
        User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));

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
    public String addSaving(SavingRequestDto dto) {
    User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
    SavingGoal goal = null;

    if(dto.getGoalId()!=null){
        goal = savingGoalRepo.findById(dto.getGoalId())
                .orElseThrow(() -> new RuntimeException("Saving goal not found"));

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
}
