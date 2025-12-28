package com.expense.exception;

public class BudgetNotSet extends RuntimeException {
    public BudgetNotSet(String message) {
        super(message);
    }
}
