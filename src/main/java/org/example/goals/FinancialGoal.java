package org.example.goals;

import org.example.exceptions.InsufficientMoneyException;
import org.example.models.Money;

import java.time.LocalDate;

class FinancialGoal {
    private final String name;
    private final Money targetAmount;
    private final LocalDate targetDate;
    private Money currentAmount;
    private final String category;

    public FinancialGoal(String name, Money targetAmount, LocalDate targetDate, String category) {
        this.name = name;
        this.targetAmount = targetAmount;
        this.targetDate = targetDate;
        this.currentAmount = new Money(targetAmount.getCurrency(), 0);
        this.category = category;
    }

    public void addContribution(Money amount) {
        currentAmount = currentAmount.add(amount);
    }

    public double getProgress() {
        return (currentAmount.getValue() / targetAmount.getValue()) * 100;
    }

    public Money getRemainingAmount() throws InsufficientMoneyException {
        return targetAmount.subtract(currentAmount);
    }

    public long getRemainingDays() {
        return LocalDate.now().until(targetDate).getDays();
    }

    public Money getRequiredMonthlySaving() throws InsufficientMoneyException {
        long remainingMonths = getRemainingDays() / 30;
        if (remainingMonths == 0) remainingMonths = 1;
        Money remainingAmount = getRemainingAmount();
        return new Money(remainingAmount.getCurrency(),
                remainingAmount.getValue() / remainingMonths);
    }
}
