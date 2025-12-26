package com.expense.utility;

public class TransactionTypeDetector {

    public static String detect(String text) {
        if (text == null || text.isBlank()) return null;
        String lower = text.toLowerCase();

        // for expense keywords
        if (lower.contains("debited") || lower.contains("spent") || lower.contains("paid") ||
                lower.contains("bought") || lower.contains("purchase") || lower.contains("booked")
                || lower.contains("subscription")) {
            return "EXPENSE";
        }

        // for income keywords
        if (lower.contains("credited") || lower.contains("received") || lower.contains("refund")
                || lower.contains("salary") || lower.contains("deposit")) {
            return "INCOME";
        }

        return "EXPENSE";
    }
}
