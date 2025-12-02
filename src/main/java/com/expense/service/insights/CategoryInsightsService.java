package com.expense.service.insights;

import com.expense.dtos.insights.CategorySummaryDto;

import java.time.LocalDate;
import java.util.List;

public interface CategoryInsightsService {

    List<CategorySummaryDto> getCategorySummary(Long userId);

    List<CategorySummaryDto> getMonthlyCategorySummary(Long userId, int month, int year);

    List<CategorySummaryDto> getRangeSummary(Long userId, LocalDate from, LocalDate to);
}
