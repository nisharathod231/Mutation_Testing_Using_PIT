package org.example.savings;

import org.example.exceptions.InsufficientMoneyException;
import org.example.manager.FinanceManager;
import org.example.models.Money;
import org.example.transactions.Transaction;

import java.util.ArrayList;
import java.util.List;

class AutomaticSavingsManager {
    private final List<SavingsRule> savingsRules;
    private final FinanceManager financeManager;

    public AutomaticSavingsManager(FinanceManager financeManager) {
        this.savingsRules = new ArrayList<>();
        this.financeManager = financeManager;
    }

    public void addSavingsRule(SavingsRule rule) {
        savingsRules.add(rule);
    }

    public void processTransaction(Transaction transaction, String accountName) {
        for (SavingsRule rule : savingsRules) {
            Money savingsAmount = rule.calculateSavings(transaction);
            if (savingsAmount.getValue() > 0) {
                try {
                    financeManager.transferBetweenAccounts(
                            accountName,
                            rule.getTargetAccount(),
                            savingsAmount,
                            "Automatic savings: " + rule.getName()
                    );
                } catch (InsufficientMoneyException e) {
                    // Log or handle the exception
                }
            }
        }
    }
}
