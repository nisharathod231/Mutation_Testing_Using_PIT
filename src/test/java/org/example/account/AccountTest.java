package org.example.account;

import org.example.budget.Budget;
import org.example.constants.TransactionType;
import org.example.exceptions.InsufficientMoneyException;
import org.example.exceptions.InvalidMoneyException;
import org.example.models.Currency;
import org.example.models.Money;
import org.example.transactions.IncomeTransaction;
import org.example.transactions.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.example.models.Currency.RUPEE;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class AccountTest {
    private Account account;
    @Before
    public void setup() {
        account = new Account("Test Account", Currency.RUPEE);
    }
    @Test
    public void shouldReturnNonEmptyBudgetMapWhenBudgetIsSetForCategory() throws InvalidMoneyException {
        Money budgetLimit = Money.createMoney(Currency.RUPEE, 5000);
        String category = "Food";
        account.setBudget(category, budgetLimit);

        Map<String, Budget> budgets = account.getBudgets();

        assertThat(budgets, is(not(Collections.emptyMap())));
        assertThat(budgets.containsKey(category), is(true));
    }

    @Test
    public void shouldRecordIncomeTransactionAndAddToTransactionHistory() throws InvalidMoneyException {
        Money income = Money.createMoney(RUPEE, 1000);
        account.addIncome(income, "Salary", "Company");

        List<Transaction> transactions = account.getTransactionHistory();
        assertThat(transactions.size(), is(1));
        assertThat(transactions.get(0).getAmount(), is(equalTo(income)));
    }

    @Test
    public void shouldRecordExpenseTransactionAndReduceBalance() throws InvalidMoneyException, InsufficientMoneyException {
        Money income = Money.createMoney(RUPEE, 1000);
        Money expense = Money.createMoney(RUPEE, 500);

        account.addIncome(income, "Salary", "Company");
        account.addExpense(expense, "Groceries", "Food");

        List<Transaction> transactions = account.getTransactionHistory();
        assertThat(transactions.size(), is(2));
        assertThat(transactions.get(1).getAmount(), is(equalTo(expense)));
        assertThat(transactions.get(1).getType(), is(TransactionType.EXPENSE));
        assertThat(transactions.get(1).getDate(), is(LocalDate.now().toString()));
    }

    @Test
    public void shouldRecordTransactionWithCorrectDateWhenIncomeIsAdded() throws InvalidMoneyException {
        Money income = Money.createMoney(Currency.RUPEE, 5000);
        String description = "Freelancing";
        String source = "Client";

        account.addIncome(income, description, source);
        List<Transaction> transactions = account.getTransactionHistory();

        assertThat(transactions.size(), is(1));
        Transaction transaction = transactions.get(0);
        assertThat(transaction.getType(), is(TransactionType.INCOME));
        assertThat(transaction.getDate(), is(LocalDate.now().toString()));
    }

    @Test(expected = InsufficientMoneyException.class)
    public void shouldThrowExceptionWhenAddingExpenseWithoutSufficientBalance() throws InvalidMoneyException, InsufficientMoneyException {
        Money expense = Money.createMoney(RUPEE, 500);
        account.addExpense(expense, "Groceries", "Food");
    }

    @Test(expected = InsufficientMoneyException.class)
    public void shouldThrowExceptionWhenWithdrawingAmountGreaterThanBalance() throws InvalidMoneyException, InsufficientMoneyException {
        Money withdrawAmount = Money.createMoney(RUPEE, 1000);
        account.withdraw(withdrawAmount);
    }

    @Test
    public void shouldAllowDepositAndWithdrawalWithoutImpactingTransactionHistory() throws InvalidMoneyException, InsufficientMoneyException {
        Money depositAmount = Money.createMoney(RUPEE, 2000);
        Money withdrawAmount = Money.createMoney(RUPEE, 1000);

        account.deposit(depositAmount);
        account.withdraw(withdrawAmount);

        assertThat(account.getTransactionHistory().size(), is(0)); // Assuming manual withdrawal and deposit don’t create transactions.
    }

    @Test
    public void shouldRecordIncomeAndExpenseInTransactionHistoryWithCorrectDetails() throws InvalidMoneyException, InsufficientMoneyException {
        Money income = Money.createMoney(RUPEE, 2000);
        Money expense = Money.createMoney(RUPEE, 1000);

        account.addIncome(income, "Freelancing", "Client");
        account.addExpense(expense, "Shopping", "Leisure");

        List<Transaction> transactions = account.getTransactionHistory();

        assertThat(transactions.size(), is(2));
        assertThat(transactions.get(0).getDescription(), is("Freelancing"));
        assertThat(transactions.get(1).getDescription(), is("Shopping"));
    }

    @Test
    public void shouldUpdateBudgetWhenExpenseIsAddedToExistingCategory() throws InvalidMoneyException, InsufficientMoneyException {
        Money budgetLimit = Money.createMoney(Currency.RUPEE, 5000);
        Money expense = Money.createMoney(Currency.RUPEE, 2000);
        String category = "Food";
        account.setBudget(category, budgetLimit);
        Money depositAmount = Money.createMoney(Currency.RUPEE, 5000);
        account.deposit(depositAmount);

        account.addExpense(expense, "Groceries", category);

        Budget budget = account.getBudgets().get(category);
        assertThat(budget.getRemainingBudget().getValue(), is(3000.0));
    }

    @Test
    public void shouldAddTransactionToTransactionHistory() throws InvalidMoneyException {
        Money amount = Money.createMoney(Currency.RUPEE, 1000);
        String description = "Salary";
        String date = LocalDate.now().toString();
        String source = "Employer";
        Transaction transaction = new IncomeTransaction(amount, description, date, source);

        account.addTransaction(transaction);

        List<Transaction> transactions = account.getTransactionHistory();
        assertThat(transactions.size(), is(1)); // Ensure transaction is added
        assertThat(transactions.get(0), is(equalTo(transaction))); // Ensure the correct transaction is added
    }

}
