package org.example.budget;

import org.example.exceptions.InsufficientMoneyException;
import org.example.models.Money;

public class Budget {
    private final String category;
    private final Money limit;
    private Money spent;

    public Budget(String category, Money limit) {
        this.category = category;
        this.limit = limit;
        this.spent = new Money(limit.getCurrency(), 0);
    }

    public void addExpense(Money expense) {
        spent = spent.add(expense);
    }

    public Money getRemainingBudget() throws InsufficientMoneyException {
        return limit.subtract(spent);
    }

    public boolean isOverBudget() {
        return spent.isLessThan(limit);
    }
}

