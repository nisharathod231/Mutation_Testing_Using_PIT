package org.example.manager;

import org.example.account.Account;
import org.example.budget.Budget;
import org.example.exceptions.InsufficientMoneyException;
import org.example.models.Money;
import org.example.transactions.Transaction;
import org.example.transactions.TransferTransaction;
import org.example.models.Currency;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FinanceManager {
    private final Map<String, Account> accounts;

    public FinanceManager() {
        this.accounts = new HashMap<>();
    }

    public void createAccount(String name, Currency defaultCurrency) {
        accounts.put(name, new Account(name, defaultCurrency));
    }

    public void transferBetweenAccounts(String fromAccount, String toAccount,
                                        Money amount, String description) throws InsufficientMoneyException {
        Account source = accounts.get(fromAccount);
        Account target = accounts.get(toAccount);

        if (source != null && target != null) {
            source.withdraw(amount);
            target.deposit(amount);

            TransferTransaction transaction = new TransferTransaction(amount, description,
                    LocalDate.now().toString(), source, target);
            source.addTransaction(transaction);
            target.addTransaction(transaction);
        }
    }

    public List<Transaction> getAccountTransactions(String accountName) {
        Account account = accounts.get(accountName);
        return account != null ? account.getTransactionHistory() : new ArrayList<>();
    }

    public Map<String, Budget> getAccountBudgets(String accountName) {
        Account account = accounts.get(accountName);
        return account != null ? account.getBudgets() : new HashMap<>();
    }
}
