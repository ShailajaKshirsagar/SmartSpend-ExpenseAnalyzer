package com.expense.controller;

import com.expense.dtos.LoginRequest;
import com.expense.dtos.RegisterUserRequest;
import com.expense.dtos.otp.SendOtpRequest;
import com.expense.dtos.otp.VerifyEmailOtpRequest;
import com.expense.dtos.otp.VerifyMobileOtpRequest;
import com.expense.exception.UserNotFoundException;
import com.expense.service.OtpService;
import com.expense.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Public API'S ",
description = "Public Endpoints for User Authentication and Authorization")
@RestController
@RequestMapping("/userauth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private OtpService otpService;

    //Register user
    @Operation(summary = "Create New user ")
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterUserRequest req){
        System.out.println("Register Api Triggered!!");
        String msg = userService.register(req);
        return new ResponseEntity<>(msg, HttpStatus.CREATED);
    }
    //login user
    @Operation(summary = "Login User ")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest req){
        String msg = userService.loginuser(req);
        return new ResponseEntity<>(msg,HttpStatus.OK);
    }

    //verify email otp
    @PostMapping("/verify-email-otp")
    public ResponseEntity<String> verifyEmailOtp(@RequestBody VerifyEmailOtpRequest req) {
        String msg = otpService.verifyEmailOtp(req);
        return new ResponseEntity<>(msg,HttpStatus.OK);
    }

    //resend otp api
    @PostMapping("/resend-otp")
    public ResponseEntity<String> resendEmailOtp(@RequestBody SendOtpRequest otpRequest){
        String msg = otpService.sendOtp(otpRequest);
        return new ResponseEntity<>(msg,HttpStatus.OK);
    }
}
