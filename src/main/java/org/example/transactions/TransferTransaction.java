package org.example.transactions;

import org.example.account.Account;
import org.example.models.Money;
import org.example.constants.TransactionType;

public class TransferTransaction extends Transaction {
    private final Account source;
    private final Account target;

    public TransferTransaction(Money amount, String description, String date,
                               Account source, Account target) {
        super(amount, description, TransactionType.TRANSFER, date);
        this.source = source;
        this.target = target;
    }

    @Override
    String getTransactionSummary() {
        return String.format("Transfer: %s - %s", getAmount(), getDescription());
    }
}
