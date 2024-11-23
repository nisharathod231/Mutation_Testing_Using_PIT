package org.example.transactions;

import org.example.models.Money;
import org.example.constants.TransactionType;

public abstract class Transaction {
    private final Money amount;
    private final String description;
    private final TransactionType type;
    private final String date;

    public Transaction(Money amount, String description, TransactionType type, String date) {
        this.amount = amount;
        this.description = description;
        this.type = type;
        this.date = date;
    }

    public Money getAmount() { return amount; }
    public String getDescription() { return description; }
    public TransactionType getType() { return type; }
    public String getDate() { return date; }

    abstract String getTransactionSummary();
}
