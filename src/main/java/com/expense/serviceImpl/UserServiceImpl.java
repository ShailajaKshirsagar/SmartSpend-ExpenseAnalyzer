package com.expense.serviceImpl;

import com.expense.dtos.LoginRequest;
import com.expense.dtos.RegisterUserRequest;
import com.expense.entity.User;
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

        User user = User.builder().name(req.getName())
                .email(req.getEmail())
                .mobno(req.getMobno())
                .password(req.getPassword())
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);
        return "User Registered Successfully";
    }

    @Override
    public String loginuser(LoginRequest req) {
        User u = userRepository.loginUser(req.getEmail(),req.getPassword());
        if(u==null){
            throw new RuntimeException("User not found");
        }
        return "Logged In successfully";
    }
}
