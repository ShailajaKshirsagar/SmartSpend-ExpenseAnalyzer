package com.expense.scheduler;

import com.expense.entity.Bill;
import com.expense.entity.User;
import com.expense.notification.EmailService;
import com.expense.repository.BillRepository;
import com.expense.repository.UserRepository;
import com.expense.utility.ResourcesLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class BillReminder {

    @Autowired
    BillRepository billRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    // Run daily at 9 AM
    //@Scheduled(cron = "0 0 9 * * ?")
    @Scheduled(cron = "0 */1 * * * ?") // for testing every minute
    public void sendBillEmailReminders() {

        //this console log -> testing
        System.out.println("bill Reminder Scheduler TRIGGERED at " + LocalDate.now());

        LocalDate today = LocalDate.now();

        userRepository.findAll()
                .forEach(user -> {

                    Long userId = user.getId();

                    billRepository.findBillsFor3DayReminder(userId, today.plusDays(3))
                            .stream()
                            .forEach(bill ->
                                    sendEmailReminder(
                                            user.getEmail(),
                                            "Bill Reminder – Due in 3 Days",
                                            buildUpcomingMail(bill, "3 days")
                                    )
                            );

                    billRepository.findBillsFor1DayReminder(userId, today.plusDays(1))
                            .stream()
                            .forEach(bill ->
                                    sendEmailReminder(
                                            user.getEmail(),
                                            "Bill Reminder – Due Tomorrow",
                                            buildUpcomingMail(bill, "1 day")
                                    )
                            );

                    billRepository.findByUserIdAndDueDateLessThanAndIsPaidFalse(
                                    userId, today.minusDays(1))
                            .stream()
                            .forEach(bill ->
                                    sendEmailReminder(
                                            user.getEmail(),
                                            "Overdue Bill Alert – 1 Day Late",
                                            buildOverdueMail(bill, "1 day")
                                    )
                            );
                });
    }

    private void sendEmailReminder(String to, String subject, String body) {
        emailService.sendEmail(to, subject, body);
    }

    private String buildUpcomingMail(Bill bill, String timeLeft) {
        String template = ResourcesLoader.load("email_templates/bill_upcoming.txt");
        return
        template.formatted(
                timeLeft,
                bill.getBillName(),
                bill.getAmount(),
                bill.getDueDate()
        );
    }

    private String buildOverdueMail(Bill bill, String overdueBy) {
        String template = ResourcesLoader.load("email_templates/bill_overdue.txt");
        return
        template.formatted(
                overdueBy,
                bill.getBillName(),
                bill.getAmount(),
                bill.getDueDate()
        );
    }
}
