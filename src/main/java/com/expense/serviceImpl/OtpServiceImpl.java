package com.expense.serviceImpl;

import com.expense.dtos.otp.SendOtpRequest;
import com.expense.dtos.otp.VerifyEmailOtpRequest;
import com.expense.dtos.otp.VerifyMobileOtpRequest;
import com.expense.entity.Otp;
import com.expense.entity.User;
import com.expense.repository.OtpRepo;
import com.expense.repository.UserRepository;
import com.expense.service.OtpService;
import com.expense.service.email.EmailService;
import com.expense.service.sms.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OtpServiceImpl implements OtpService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    OtpRepo otpRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EmailService emailService;

    @Autowired
    SmsService smsService;

    private String generateOtp(){
        return  String.valueOf((int)(Math.random() * 900000) + 100000);
    }

    @Override
    public String sendOtp(SendOtpRequest req) {

        Optional<User> user = Optional.empty();
        if (req.getEmail() != null && !req.getEmail().isBlank()) {
            user = userRepository.findByEmail(req.getEmail());
        }
        if (req.getEmail() != null && user.get().getEmailVerified()) {
            throw new RuntimeException("Email already verified");
        }
        else if (req.getMobile() != null && !req.getMobile().isBlank()) {
            user = userRepository.findByMobile(req.getMobile());
        }
        if(user.isEmpty()){
           throw new RuntimeException("User not found. Register First!!");
       }

        String otp = generateOtp();
       String hashedotp = passwordEncoder.encode(otp);
        Otp otpmap = Otp.builder()
                .email(req.getEmail())
                .mobile(req.getMobile())
                .otpHash(hashedotp)
                .createdAt(LocalDateTime.now()).build();
        otpRepository.save(otpmap);

        if (req.getEmail() != null) {
            emailService.sendEmailOtp(req.getEmail(), otp);
        }

        return "OTP Sent Successfully!!";

               // "Your OTP is --> " + otp;
        //returning otp for testing purpose only because it is hashed in db and hashedotp is only for db
        //will send otp (original not hashed) to user through email and mobile
        //will make changes while integrating smtp,sms service
    }

    @Override
    public String verifyEmailOtp(VerifyEmailOtpRequest req) {
        Optional<User> user = userRepository.findByEmail(req.getEmail());
        if(user.isEmpty()){
            throw new RuntimeException("User not found!!");
        }
        List<Otp> otpList = otpRepository.findLatestEmailOtp(req.getEmail());
        if(otpList.isEmpty()){
            throw new RuntimeException("No OTP Found!!");
        }

        Otp emailOtp = otpList.get(0);
        // time limit
        long seconds = Duration.between(emailOtp.getCreatedAt(), LocalDateTime.now()).getSeconds();
        if (seconds > 180) {
            throw new RuntimeException("OTP expired! Try again.");
        }
        if (!passwordEncoder.matches(req.getOtp(), emailOtp.getOtpHash())) {
            throw new RuntimeException("Invalid OTP!!");
        }
        User userEntity = user.get();
        userEntity.setEmailVerified(true);
        userRepository.save(userEntity);

        return "Email Verified Successfully!!";
    }

    @Override
    public String verifyMobileOtp(VerifyMobileOtpRequest req) {
        Optional<User> user = userRepository.findByMobile(req.getMobile());
        if (user.isEmpty()) {
            throw new RuntimeException("User not found!!");
        }
        List<Otp> otpList = otpRepository.findLatestMobileOtp(req.getMobile());
        if (otpList.isEmpty()) {
            throw new RuntimeException("No OTP Found!!");
        }
        Otp mobileOtp = otpList.get(0);
        long seconds = Duration.between(mobileOtp.getCreatedAt(), LocalDateTime.now()).getSeconds();
        if (seconds > 180) {
            throw new RuntimeException("OTP Expired! Try again");
        }
        if (!passwordEncoder.matches(req.getOtp(), mobileOtp.getOtpHash())) {
            throw new RuntimeException("Invalid Otp!!");
        }
        return "Mobile Number Verified Successfully!!";
    }
}
