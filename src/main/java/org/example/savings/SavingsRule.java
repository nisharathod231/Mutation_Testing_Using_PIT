package org.example.savings;

import org.example.constants.TransactionType;
import org.example.models.Money;
import org.example.transactions.Transaction;

class SavingsRule {
    private final String name;
    private final RuleType type;
    private final double parameter;
    private final String targetAccount;

    public enum RuleType {
        PERCENTAGE_OF_INCOME,
        ROUND_UP,
        FIXED_AMOUNT
    }

    public SavingsRule(String name, RuleType type, double parameter, String targetAccount) {
        this.name = name;
        this.type = type;
        this.parameter = parameter;
        this.targetAccount = targetAccount;
    }

    public Money calculateSavings(Transaction transaction) {
        if (transaction.getType() != TransactionType.INCOME && type == RuleType.PERCENTAGE_OF_INCOME) {
            return new Money(transaction.getAmount().getCurrency(), 0);
        }

        return switch (type) {
            case PERCENTAGE_OF_INCOME -> {
                double amount = transaction.getAmount().getValue() * (parameter / 100);
                yield new Money(transaction.getAmount().getCurrency(), amount);
            }
            case ROUND_UP -> {
                double transactionAmount = transaction.getAmount().getValue();
                double roundUp = Math.ceil(transactionAmount) - transactionAmount;
                if (roundUp < parameter) roundUp = parameter;
                yield new Money(transaction.getAmount().getCurrency(), roundUp);
            }
            case FIXED_AMOUNT -> new Money(transaction.getAmount().getCurrency(), parameter);
        };
    }

    public String getTargetAccount() {
        return targetAccount;
    }

    public String getName() {
        return name;
    }
}
