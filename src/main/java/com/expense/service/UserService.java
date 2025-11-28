package com.expense.service;

import com.expense.dtos.LoginRequest;
import com.expense.dtos.RegisterUserRequest;

public interface UserService {
    String register(RegisterUserRequest req);

    String loginuser(LoginRequest req);
}
