package org.example.transactions;

import org.example.models.Money;
import org.example.constants.TransactionType;

public class IncomeTransaction extends Transaction {
    private final String source;

    public IncomeTransaction(Money amount, String description, String date, String source) {
        super(amount, description, TransactionType.INCOME, date);
        this.source = source;
    }

    @Override
    String getTransactionSummary() {
        return String.format("Income from %s: %s - %s", source, getAmount(), getDescription());
    }

    public String getSource() {
        return source;
    }
}
