package com.expense.serviceImpl.ai;

import com.expense.ai_client.CohereClient;
import com.expense.dtos.ai.AutoCategorizationResponseDto;
import com.expense.entity.Category;
import com.expense.entity.Expense;
import com.expense.entity.Income;
import com.expense.entity.User;
import com.expense.repository.CategoryRepo;
import com.expense.repository.ExpenseRepo;
import com.expense.repository.IncomeRepo;
import com.expense.repository.UserRepository;
import com.expense.service.ai.AutoCategorizationService;
import com.expense.utility.AmountExtractor;
import com.expense.utility.NotificationValidator;
import com.expense.utility.TransactionTypeDetector;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AutoCategorizationServiceImpl implements AutoCategorizationService {

    @Autowired
    private ExpenseRepo expenseRepo;
    @Autowired
    private IncomeRepo incomeRepo;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CohereClient cohereClient;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private AIAsyncService aiAsyncService;

    @Override
    public AutoCategorizationResponseDto process(String notificationText, Long userId) {

        if (!NotificationValidator.isTransaction(notificationText)) {
            return new AutoCategorizationResponseDto("FAILED", "Not a financial notification");
        }

        User user = userRepository.findById(userId).orElseThrow();
        List<String> savedTransactions = new ArrayList<>();

        // Split notification into sub-transactions by 'and', ',' or ';'
        String[] parts = notificationText.split(" and |,|;");
        for (String part : parts) {
            part = part.trim();
            if (part.isBlank()) continue;

            BigDecimal amount = AmountExtractor.extractAmount(part);
            if (amount == null) continue;

            String type = TransactionTypeDetector.detect(part);
            if (type == null) type = "EXPENSE";

            String categoryName = cohereClient.categorize(part, type);
            if (categoryName == null || categoryName.isBlank()) categoryName = "Other";

            // Save category in Category
            Category category= Category.builder()
                    .name(categoryName)
                    .type(type)
                    .createdAt(LocalDate.now())
                    .user(user)
                    .build();
            categoryRepo.save(category);

            // Save transaction
            if (type.equalsIgnoreCase("EXPENSE")) {
                Expense expense = Expense.builder()
                        .amount(amount.doubleValue())
                        .category(categoryName)
                        .date(LocalDate.now())
                        .title(part)
                        .user(user)
                        .build();
                Expense saved = expenseRepo.save(expense);
                aiAsyncService.categorizeExpenseAsync(saved.getId(), notificationText,type);
            } else {
                Income income = Income.builder()
                        .amount(amount.doubleValue())
                        .source(categoryName)
                        .date(LocalDate.now())
                        .notes(part)
                        .user(user)
                        .build();
                Income saved = incomeRepo.save(income);
                aiAsyncService.categorizeIncomeAsync(saved.getIncomeId(), notificationText,type);
            }

            savedTransactions.add(type + " saved with category " + categoryName + " and amount ₹" + amount);
        }

        if (savedTransactions.isEmpty()) {
            return new AutoCategorizationResponseDto("FAILED", "Could not extract any transaction from notification");
        }

        // Return all saved transactions in response
        return new AutoCategorizationResponseDto("SUCCESS", String.join("; ", savedTransactions));
    }
}
