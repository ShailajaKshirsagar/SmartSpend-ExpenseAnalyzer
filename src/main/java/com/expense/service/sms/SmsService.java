package com.expense.service.sms;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    @Value("${twilio.phone-number}")
    private String fromNumber;

    public void sendOtp(String toMobile, String otp) {

        Message.creator(
                new PhoneNumber(toMobile),
                new PhoneNumber(fromNumber),
                "Your OTP for Expense Analyzer is: " + otp
        ).create();
    }
}
