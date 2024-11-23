package org.example.account;

import org.example.budget.Budget;
import org.example.exceptions.InsufficientMoneyException;
import org.example.exceptions.InvalidMoneyException;
import org.example.models.Currency;
import org.example.models.Money;
import org.example.models.Wallet;
import org.example.transactions.ExpenseTransaction;
import org.example.transactions.IncomeTransaction;
import org.example.transactions.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Account {
    private final String name;
    private final Wallet wallet;
    private final List<Transaction> transactions;
    private final Map<String, Budget> budgets;

    public Account(String name, Currency defaultCurrency) {
        this.name = name;
        this.wallet = new Wallet();
        this.transactions = new ArrayList<>();
        this.budgets = new HashMap<>();
    }

    public void addIncome(Money amount, String description, String source)
            throws InvalidMoneyException {
        IncomeTransaction transaction = new IncomeTransaction(amount, description,
                LocalDate.now().toString(), source);
        wallet.deposit(amount);
        transactions.add(transaction);
    }

    public void addExpense(Money amount, String description, String category)
            throws InvalidMoneyException, InsufficientMoneyException {
        ExpenseTransaction transaction = new ExpenseTransaction(amount, description,
                LocalDate.now().toString(), category);
        wallet.withdraw(amount);
        transactions.add(transaction);

        if (budgets.containsKey(category)) {
            budgets.get(category).addExpense(amount);
        }
    }

    public void setBudget(String category, Money limit) {
        budgets.put(category, new Budget(category, limit));
    }

    public List<Transaction> getTransactionHistory() {
        return new ArrayList<>(transactions);
    }

    public Map<String, Budget> getBudgets() {
        return new HashMap<>(budgets);
    }

    public void withdraw(Money money) throws InsufficientMoneyException {
        wallet.withdraw(money);
    }

    public void deposit(Money money) {
        wallet.deposit(money);
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }
}
