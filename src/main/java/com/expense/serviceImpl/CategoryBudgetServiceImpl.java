package com.expense.serviceImpl;

import com.expense.dtos.budget_savings.CategoryBudgetRequest;
import com.expense.dtos.budget_savings.CategoryBudgetResponseDto;
import com.expense.entity.Category;
import com.expense.entity.CategoryBudget;
import com.expense.entity.User;
import com.expense.exception.CategoryNotFound;
import com.expense.exception.UserNotFoundException;
import com.expense.repository.CategoryBudgetRepo;
import com.expense.repository.CategoryRepo;
import com.expense.repository.UserRepository;
import com.expense.service.CategoryBudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryBudgetServiceImpl implements CategoryBudgetService {

    @Autowired
    CategoryBudgetRepo categoryBudgetRepo;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CategoryRepo categoryRepo;

    @Override
    public CategoryBudgetResponseDto setCategoryBudget(CategoryBudgetRequest req, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->  new UserNotFoundException("User Not Found"));

        Category category = categoryRepo.findById(req.getCategoryId())
                .orElseThrow(() -> new CategoryNotFound("Category not found"));
        LocalDate now = LocalDate.now();
        int month = now.getMonthValue();
        int year = now.getYear();

        CategoryBudget cb = categoryBudgetRepo
                .findByUserIdAndCategoryIdAndMonthAndYear(user.getId(), category.getId(), month, year)
                .orElse(CategoryBudget.builder()
                        .user(user)
                        .category(category)
                        .month(month)
                        .year(year)
                        .spentAmount(0.0)
                        .build());
        cb.setLimitAmount(req.getLimitAmount());
        categoryBudgetRepo.save(cb);
        return toDto(cb);
    }

    //to deduct expense
    @Override
    public void deductExpense(Long userId, Long categoryId, Double amount) {
        LocalDate now = LocalDate.now();
        int month = now.getMonthValue();
        int year = now.getYear();

        CategoryBudget cb = categoryBudgetRepo.findByUserIdAndCategoryIdAndMonthAndYear(userId,categoryId,month,year)
                .orElse(null);
        if(cb==null) {
            return;  //no budget set
        }
        cb.setSpentAmount(cb.getSpentAmount()+amount);
        categoryBudgetRepo.save(cb);
    }

    @Override
    public List<CategoryBudgetResponseDto> getCategoryBudgets(Long userId) {
        return categoryBudgetRepo.findAll()
                .stream()
                .filter(b -> b.getUser().getId().equals(userId))
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private CategoryBudgetResponseDto toDto(CategoryBudget c){
        return CategoryBudgetResponseDto.builder()
                .id(c.getId())
                .categoryId(c.getCategory().getId())
                .categoryName(c.getCategory().getName())
                .limitAmount(c.getLimitAmount())
                .spentAmount(c.getSpentAmount())
                .remainingAmount(c.getLimitAmount() - c.getSpentAmount())
                .month(c.getMonth())
                .year(c.getYear())
                .build();
    }
}
