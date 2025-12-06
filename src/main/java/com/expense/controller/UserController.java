package com.expense.controller;

import com.expense.dtos.LoginRequest;
import com.expense.dtos.RegisterUserRequest;
import com.expense.dtos.otp.SendOtpRequest;
import com.expense.dtos.otp.VerifyOtpRequest;
//import com.expense.service.OtpService;
import com.expense.service.OtpService;
import com.expense.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userauth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private OtpService otpService;

    //Register user
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterUserRequest req){
        String msg = userService.register(req);
        return new ResponseEntity<>(msg, HttpStatus.CREATED);
    }
    //login user
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest req){
        String msg = userService.loginuser(req);
        return new ResponseEntity<>(msg,HttpStatus.OK);
    }

}
