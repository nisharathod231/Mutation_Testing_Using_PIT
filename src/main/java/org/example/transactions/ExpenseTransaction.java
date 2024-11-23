package org.example.transactions;

import org.example.models.Money;
import org.example.constants.TransactionType;

public class ExpenseTransaction extends Transaction {
    private final String category;

    public ExpenseTransaction(Money amount, String description, String date, String category) {
        super(amount, description, TransactionType.EXPENSE, date);
        this.category = category;
    }

    @Override
    String getTransactionSummary() {
        return String.format("Expense in %s: %s - %s", category, getAmount(), getDescription());
    }

    public String getCategory() {
        return category;
    }
}
