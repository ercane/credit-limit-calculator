package com.mree.app.core.util;

public class CreditLimitCalculator {
    private static final Integer CREDIT_LINIT_MULTIPLIER = 4;
    private static final Double MONTHLY_INCOME_MIN_LIMIT = 5000.0;

    public static Double calculateCreditLimit(Integer creditPoint, Double monthlyIncome) {
        if (creditPoint < 500) {
            return 0.0;
        } else if (creditPoint < 1000 && monthlyIncome < MONTHLY_INCOME_MIN_LIMIT) {
            return 0.0;
        } else if (creditPoint < 1000 && monthlyIncome >= MONTHLY_INCOME_MIN_LIMIT) {
            return 10000.0;
        } else {
            return monthlyIncome * CREDIT_LINIT_MULTIPLIER;
        }
    }
}
