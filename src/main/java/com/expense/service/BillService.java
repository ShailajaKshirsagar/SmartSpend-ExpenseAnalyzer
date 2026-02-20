package com.expense.service;

import com.expense.dtos.otp.BillResponseDto;
import com.expense.entity.Bill;

import java.time.LocalDate;
import java.util.List;

public interface BillService {
    String addBill(Bill bill, Long userId);

    List<BillResponseDto> getUpcomingBills(Long userId, LocalDate currentDate);
    List<BillResponseDto> getOverdueBills(Long userId, LocalDate currentDate);
}
