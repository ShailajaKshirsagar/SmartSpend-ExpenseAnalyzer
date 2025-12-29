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
    @Scheduled(cron = "0 0 9 * * ?")
    //@Scheduled(cron = "0 */1 * * * ?") // for testing every minute
    public void sendBillEmailReminders() {

        //this console log -> testing
        System.out.println("bill Reminder Scheduler TRIGGERED at " + LocalDate.now());

        LocalDate today = LocalDate.now();
        LocalDate threeDaysLater = today.plusDays(3); // Upcoming reminder 3 days before
        LocalDate oneDayLater = today.plusDays(1);    // Upcoming reminder 1 day before
        LocalDate oneDayAfter = today.minusDays(1); // Overdue reminder 1 day after

        List<User> users = userRepository.findAll();

        for (User user : users) {
            Long userId = user.getId();
            // 3 Days Before Due Date
            List<Bill> bills3Days = billRepository.findBillsFor3DayReminder(userId, threeDaysLater);
            for (Bill bill : bills3Days) {
                emailService.sendEmail(
                        user.getEmail(),
                        "Bill Reminder – Due in 3 Days",
                        buildUpcomingMail(bill, "3 days")
                );
                bill.setReminder3DaysSent(true);
                billRepository.save(bill);
            }

            // 1 Day Before Due Date
            List<Bill> bills1Day = billRepository.findBillsFor1DayReminder(userId, oneDayLater);
            for (Bill bill : bills1Day) {
                emailService.sendEmail(
                        user.getEmail(),
                        "Bill Reminder – Due Tomorrow",
                        buildUpcomingMail(bill, "1 day")
                );
                bill.setReminder1DaySent(true);
                billRepository.save(bill);
            }

            List<Bill> overdueBills = billRepository.findByUserIdAndDueDateLessThanAndIsPaidFalse(userId, oneDayAfter);
            for (Bill bill : overdueBills) {
                emailService.sendEmail(
                        user.getEmail(),
                        "Overdue Bill Alert – 1 Day Late",
                        buildOverdueMail(bill, "1 day")
                );
                bill.setOverdue1DaySent(true);
                billRepository.save(bill);
            }
        }
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
