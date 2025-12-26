package com.expense.serviceImpl.ai;

import com.expense.ai_client.CohereClient;
import com.expense.repository.ExpenseRepo;
import com.expense.repository.IncomeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

//This async class is for making api call faster,
// does not wait too long for ai response do the task in background
@Service
@RequiredArgsConstructor
public class AIAsyncService {

    @Autowired
    private CohereClient cohereClient;
    @Autowired
    private ExpenseRepo expenseRepo;
    @Autowired
    private IncomeRepo incomeRepo;

    @Async
    public void categorizeExpenseAsync(Long expenseId,String text,String type){
        String category = cohereClient.categorize(text,type);
        expenseRepo.findById(expenseId).ifPresent(e->{
            e.setCategory(category);
            expenseRepo.save(e);
        });
    }

    @Async
    public void categorizeIncomeAsync(Long incomeId,String text,String type){
        String category = cohereClient.categorize(text,type);
        incomeRepo.findById(incomeId).ifPresent(i -> {
            i.setSource(category);
            incomeRepo.save(i);
        });
    }

}
