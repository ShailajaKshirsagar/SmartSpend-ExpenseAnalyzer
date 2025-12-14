package com.expense.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Async
    public void sendEmailOtp(String toEmail,String otp){

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(toEmail);
        mailMessage.setSubject("Expense Analyzer - Email OTP");
        mailMessage.setText("Your OTP IS : " + otp + "\nThis OTP is valid for 3 minutes!");

        javaMailSender.send(mailMessage);
        System.out.println("OTP sent to email: " + toEmail + " OTP: " + otp);
    }
}
