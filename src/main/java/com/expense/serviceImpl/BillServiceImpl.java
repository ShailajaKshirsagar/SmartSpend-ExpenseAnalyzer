package com.expense.serviceImpl;

import com.expense.dtos.otp.BillResponseDto;
import com.expense.entity.Bill;
import com.expense.entity.User;
import com.expense.exception.UserNotFoundException;
import com.expense.repository.BillRepository;
import com.expense.repository.UserRepository;
import com.expense.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    BillRepository billRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public String addBill(Bill bill, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->  new UserNotFoundException("User Not Found"));
        bill.setUserId(user.getId());
        billRepository.save(bill);
        return "Bill added";
    }

    @Override
    public List<BillResponseDto> getUpcomingBills(Long userId) {
        List<Bill> dto = billRepository.findByUserIdAndDueDateGreaterThanEqualAndIsPaidFalse(userId, LocalDate.now());
        return dto.stream()
                .map(bill -> BillResponseDto.builder()
                        .billName(bill.getBillName())
                        .amount(bill.getAmount())
                        .dueDate(bill.getDueDate())
                        .isRecurring(bill.getIsRecurring())
                        .isPaid(bill.getIsPaid())
                        .build()).toList();
    }

    @Override
    public List<BillResponseDto> getOverdueBills(Long userId) {
        List<Bill> dto = billRepository.findByUserIdAndDueDateLessThanAndIsPaidFalse(userId, LocalDate.now());
        return dto.stream()
                .map(bill -> BillResponseDto.builder()
                        .billName(bill.getBillName())
                        .amount(bill.getAmount())
                        .dueDate(bill.getDueDate())
                        .isRecurring(bill.getIsRecurring())
                        .isPaid(bill.getIsPaid())
                        .build()).toList();
    }

    //Auto repeat monthly bills using schedulers(for daily schedules)
    public  void generateRecurringBills(){
        List<Bill> bills = billRepository.findAll();
        LocalDate today = LocalDate.now();

        for(Bill bill:bills){
            if(bill.getIsRecurring() && bill.getDueDate().isBefore(today) && !bill.getIsPaid()){
                Bill newBill = Bill.builder()
                        .userId(bill.getUserId())
                        .billName(bill.getBillName())
                        .amount(bill.getAmount())
                        .isRecurring(true)
                        .isPaid(false)
                        .dueDate(bill.getDueDate().plusMonths(1))
                        .build();
                billRepository.save(newBill);
            }
        }
    }
}
