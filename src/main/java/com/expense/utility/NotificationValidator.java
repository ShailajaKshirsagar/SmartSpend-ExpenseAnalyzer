package com.expense.utility;

//This is for back end validations.
public class NotificationValidator {
    public  static boolean isTransaction(String text){
        String lower = text.toLowerCase();
        return lower.contains("₹") ||
                lower.contains("rs") ||
                lower.contains("debited") ||
                lower.contains("credited") ||
                lower.contains("spent")  ||
                lower.contains("received");
    }
}
