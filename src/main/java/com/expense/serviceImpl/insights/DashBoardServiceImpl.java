package com.expense.serviceImpl.insights;

import com.expense.dtos.insights.DashBoardResponseDto;
import com.expense.dtos.insights.RecentTransactionDto;
import com.expense.entity.Expense;
import com.expense.entity.Income;
import com.expense.repository.ExpenseRepo;
import com.expense.repository.IncomeRepo;
import com.expense.service.insights.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DashBoardServiceImpl implements DashboardService {

    @Autowired
    private ExpenseRepo expenseRepo;

    @Autowired
    private IncomeRepo incomeRepo;

    @Override
    public DashBoardResponseDto getDashboard(Long userId) {
        LocalDate now = LocalDate.now();
        int month = now.getMonthValue();
        int year = now.getYear();

        Double monthExpense = expenseRepo.getMonthlyTotal(userId,month,year);

        Double monthIncome = incomeRepo.getMonthlyTotal(userId,month,year);
        Double totalBalance = monthIncome - monthExpense;

        Pageable pageable = PageRequest.of(0, 5);
        List<Expense> recentExpenses = expenseRepo
                .findRecentExpenses(userId, pageable).getContent();

        List<RecentTransactionDto> recent = recentExpenses.stream()
                .map(e -> new RecentTransactionDto(
                        e.getTitle(),
                        e.getDate(),
                        e.getAmount()
                ))
                .toList();

        List<Income> recentIncome = incomeRepo
                .findRecentIncome(userId, pageable).getContent();

        List<RecentTransactionDto> income = recentIncome.stream()
                .map(i -> new RecentTransactionDto(
                        i.getSource(),
                        i.getDate(),
                        i.getAmount()
                ))
                .toList();

        return DashBoardResponseDto.builder()
                .thisMonthExpense(monthExpense)
                .thisMonthIncome(monthIncome)
                .totalBalance(totalBalance)
                .recentTransactions(recent)
                .build();
    }
}
