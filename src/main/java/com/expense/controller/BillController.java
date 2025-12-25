package com.expense.controller;

import com.expense.dtos.otp.BillResponseDto;
import com.expense.entity.Bill;
import com.expense.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bills")
public class BillController {

    @Autowired
    BillService billService;

    @PostMapping("/add-bill")
    public ResponseEntity<String> addBill(@RequestBody Bill bill,Authentication authentication){
        Long userId = (Long) authentication.getPrincipal();
        String msg= billService.addBill(bill,userId);
        return new ResponseEntity<>(msg, HttpStatus.CREATED);
    }

    @GetMapping("/get-upcoming-bills")
    public ResponseEntity<List<BillResponseDto>> getUpcomingBills(Authentication authentication){
        Long userId = (Long) authentication.getPrincipal();
        List<BillResponseDto> billList = billService.getUpcomingBills(userId);
        return new ResponseEntity<>(billList,HttpStatus.OK);
    }

    @GetMapping("/get-overdue-bills")
    public ResponseEntity<List<BillResponseDto>> getOverdueBills(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        List<BillResponseDto> billList = billService.getOverdueBills(userId);
        return new ResponseEntity<>(billList,HttpStatus.OK);
    }

}
