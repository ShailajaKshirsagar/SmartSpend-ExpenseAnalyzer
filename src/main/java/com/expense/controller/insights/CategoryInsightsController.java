package com.expense.controller.insights;

import com.expense.dtos.insights.CategorySummaryDto;
import com.expense.service.insights.CategoryInsightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/categories/insights")
public class CategoryInsightsController {

    @Autowired
    private CategoryInsightsService categoryInsightsService;

    @GetMapping("/getCategory-Summary")
    public ResponseEntity<List<CategorySummaryDto>> getCategorySummary(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        List<CategorySummaryDto> categorySummaryList = categoryInsightsService.getCategorySummary(userId);
        return new ResponseEntity<>(categorySummaryList, HttpStatus.OK);
    }

    @GetMapping("/getCategory-Monthly-Summary")
    public ResponseEntity<List<CategorySummaryDto>> getMonthlyCategorySummary(
            @RequestParam int month, @RequestParam int year,Authentication authentication){
        Long userId = (Long) authentication.getPrincipal();
        List<CategorySummaryDto> categorySummaryList = categoryInsightsService.getMonthlyCategorySummary(userId,month,year);
        return new ResponseEntity<>(categorySummaryList, HttpStatus.OK);
    }

    @GetMapping("/get-summary-range")
    public ResponseEntity<List<CategorySummaryDto>> getRangeSummary(
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate from,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate to,
            Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        List<CategorySummaryDto> categorySummaryList = categoryInsightsService.getRangeSummary(userId,from,to);
        return new ResponseEntity<>(categorySummaryList, HttpStatus.OK);
    }
}
