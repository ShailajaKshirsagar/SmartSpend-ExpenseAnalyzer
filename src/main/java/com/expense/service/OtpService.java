package com.expense.service;

import com.expense.dtos.otp.SendOtpRequest;
import com.expense.dtos.otp.VerifyEmailOtpRequest;
import com.expense.dtos.otp.VerifyMobileOtpRequest;

public interface OtpService {

    String sendOtp(SendOtpRequest req);

    String verifyEmailOtp(VerifyEmailOtpRequest req);

    String verifyMobileOtp(VerifyMobileOtpRequest req);

}
