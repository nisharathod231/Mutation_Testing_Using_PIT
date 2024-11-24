package org.example.manager;

import org.example.budget.Budget;
import org.example.exceptions.InsufficientMoneyException;
import org.example.exceptions.InvalidMoneyException;
import org.example.models.Currency;
import org.example.models.Money;
import org.example.transactions.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;

public class FinanceManagerTest {
    private FinanceManager financeManager;

    @Before
    public void setup() {
        financeManager = new FinanceManager();
    }
    @Test
    public void shouldStoreAccountWhenCreated() {
        financeManager.createAccount("Savings", Currency.RUPEE);

        assertThat(financeManager.getAccountBudgets("Savings"), is(notNullValue()));
    }

    @Test
    public void shouldInitializeAccountsMapOnCreation() {
        assertThat(financeManager.getAccountBudgets("NonExistent"), is(notNullValue()));
    }

    @Test
    public void shouldAddCorrectAccountToAccountsMap() {
        financeManager.createAccount("Business", Currency.RUPEE);

        assertThat(financeManager.getAccountTransactions("Business"), is(empty()));
    }

    @Test(expected = InsufficientMoneyException.class)
    public void shouldFailTransferWhenSourceAccountHasInsufficientBalance() throws InsufficientMoneyException, InvalidMoneyException {
        financeManager.createAccount("Account1", Currency.RUPEE);
        financeManager.createAccount("Account2", Currency.RUPEE);

        Money transferAmount = Money.createMoney(Currency.RUPEE, 2000);

        financeManager.transferBetweenAccounts("Account1", "Account2", transferAmount, "Transfer");
    }

    @Test
    public void shouldReturnEmptyTransactionHistoryForNonexistentAccount() {
        List<Transaction> transactions = financeManager.getAccountTransactions("NonExistent");

        assertThat(transactions, is(notNullValue()));
        assertThat(transactions, is(empty()));
    }

    @Test
    public void shouldReturnEmptyBudgetForNonexistentAccount() {
        Map<String, Budget> budgets = financeManager.getAccountBudgets("NonExistent");

        assertThat(budgets, is(notNullValue()));
        assertThat(budgets.isEmpty(), is(true));
    }
}
