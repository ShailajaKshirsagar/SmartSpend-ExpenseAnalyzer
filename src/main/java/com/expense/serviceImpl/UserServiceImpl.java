package com.expense.serviceImpl;

import com.expense.dtos.LoginRequest;
import com.expense.dtos.RegisterUserRequest;
import com.expense.entity.User;
import com.expense.exception.EmailAlreadyExistException;
import com.expense.exception.InvalidCredentialsException;
import com.expense.repository.UserRepository;
import com.expense.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepository userRepository;

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

        return "User Registered Successfully";
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
        return "Logged In successfully";
    }
}
