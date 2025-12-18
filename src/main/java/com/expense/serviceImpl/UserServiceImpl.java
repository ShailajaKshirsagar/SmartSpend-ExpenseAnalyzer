package com.expense.serviceImpl;

import com.expense.dtos.LoginRequest;
import com.expense.dtos.RegisterUserRequest;
import com.expense.dtos.otp.SendOtpRequest;
import com.expense.entity.User;
import com.expense.exception.EmailAlreadyExistException;
import com.expense.exception.InvalidCredentialsException;
import com.expense.repository.UserRepository;
import com.expense.service.OtpService;
import com.expense.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    OtpService otpService;

    @Override
    public String register(RegisterUserRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new EmailAlreadyExistException("Email is already registered");
        }
        User user = User.builder()
                .name(req.getName())
                .email(req.getEmail())
                .mobile(req.getMobno())
                .password(req.getPassword())
                .createdAt(LocalDateTime.now())
                .build();
        userRepository.save(user);

        //sends otp automatically
        SendOtpRequest otpRequest = SendOtpRequest.builder()
                .email(user.getEmail())
                .mobile(user.getMobile())
                .build();

        otpService.sendOtp(otpRequest);

        return "User registered successfully! OTP sent to Email and Mobile.";
}

    @Override
    public String loginuser(LoginRequest req) {
        // Find user by email
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));
        // Check password
        if (!user.getPassword().equals(req.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }
        if (!user.getEmailVerified()) {
            throw new RuntimeException("Please verify your email before login");
        }
        return "Logged In successfully";
    }
}
