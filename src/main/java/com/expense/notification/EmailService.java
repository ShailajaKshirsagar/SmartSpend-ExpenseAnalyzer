package com.expense.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Async
    public void sendEmailOtp(String toEmail,String otp){

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(toEmail);
        mailMessage.setSubject("Expense Analyzer - Email OTP");
        mailMessage.setText("Your OTP IS : " + otp + "\nThis OTP is valid for 3 minutes!");

        javaMailSender.send(mailMessage);
        System.out.println("OTP sent to email: " + toEmail + " OTP: " + otp);
    }

    public void sendEmail(String to, String subject , String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        javaMailSender.send(message);
    }

}
