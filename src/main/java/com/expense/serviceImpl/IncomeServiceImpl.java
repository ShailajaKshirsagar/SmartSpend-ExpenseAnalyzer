package com.expense.serviceImpl;

import com.expense.dtos.ExpenseResponseDto;
import com.expense.dtos.IncomeRequestDto;
import com.expense.dtos.IncomeResponseDto;
import com.expense.entity.Income;
import com.expense.entity.User;
import com.expense.repository.IncomeRepo;
import com.expense.repository.UserRepository;
import com.expense.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class IncomeServiceImpl implements IncomeService {

    @Autowired
    private IncomeRepo incomeRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public String createIncome(Long userId, IncomeRequestDto dto) {
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not found"));
        Income income = Income.builder()
                .amount(dto.getAmount())
                .date(LocalDate.now())
                .source(dto.getSource())
                .notes(dto.getNotes())
                .user(user).build();
        incomeRepository.save(income);
        return "Income Saved Successfully";
    }

    @Override
    public List<IncomeResponseDto> getIncome(Long userId) {
            List<Income> incomes =  incomeRepository.findByUserId(userId);
            return  incomes.stream()
                .map(income -> new IncomeResponseDto(
                       income.getAmount(),
                        income.getSource(),
                        income.getNotes()
                ))
                .toList();
    }
}
