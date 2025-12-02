package com.expense.serviceImpl.insights;

import com.expense.dtos.insights.CategorySummaryDto;
import com.expense.repository.ExpenseRepo;
import com.expense.service.insights.CategoryInsightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CategorySummaryServiceImpl implements CategoryInsightsService {

    @Autowired
    private ExpenseRepo expenseRepository;

    @Override
    public List<CategorySummaryDto> getCategorySummary(Long userId) {
        return expenseRepository.getCategorySummary(userId);
    }

    @Override
    public List<CategorySummaryDto> getMonthlyCategorySummary(Long userId, int month, int year) {
        return expenseRepository.getMonthlyCategorySummary(userId, month, year);
    }

    @Override
    public List<CategorySummaryDto> getRangeSummary(Long userId, LocalDate from, LocalDate to) {
        return expenseRepository.getRangeCategorySummary(userId, from, to);
    }
}
