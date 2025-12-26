package com.expense.utility;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//This class is for extracting amount from notification , it can also be done from frontend but here it is for backup,
// validations are mandatory in the back end to avoid conflict

public class AmountExtractor {

    private static final Pattern AMOUNT_PATTERN =
            Pattern.compile("(₹|rs\\.?\\s?)([\\d,]+(?:\\.\\d{1,2})?)");


    public static BigDecimal extractAmount(String text) {
        Matcher matcher = AMOUNT_PATTERN.matcher(text.toLowerCase());
        if (matcher.find()) {
            String amountStr = matcher.group(2).replaceAll(",", ""); // remove commas
            return new BigDecimal(amountStr);
        }
        return null;
    }
}
