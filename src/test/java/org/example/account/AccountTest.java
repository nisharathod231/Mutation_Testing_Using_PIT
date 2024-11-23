package org.example.account;

import org.example.exceptions.InsufficientMoneyException;
import org.example.exceptions.InvalidMoneyException;
import org.example.models.Currency;
import org.example.models.Money;
import org.example.transactions.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.example.models.Currency.RUPEE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AccountTest {

    private Account account;

    @Before
    public void setup() {
        account = new Account("Test Account", Currency.RUPEE);
    }

    @Test
    public void testAddIncome() throws InvalidMoneyException {
        Money income = Money.createMoney(RUPEE, 1000);
        account.addIncome(income, "Salary", "Company");

        List<Transaction> transactions = account.getTransactionHistory();
        assertThat(transactions.size(), is(1));
        assertThat(transactions.get(0).getAmount(), is(equalTo(income)));
    }

    @Test
    public void testAddExpense() throws InvalidMoneyException, InsufficientMoneyException {
        Money income = Money.createMoney(RUPEE, 1000);
        Money expense = Money.createMoney(RUPEE, 500);

        account.addIncome(income, "Salary", "Company");
        account.addExpense(expense, "Groceries", "Food");

        List<Transaction> transactions = account.getTransactionHistory();
        assertThat(transactions.size(), is(2));
        assertThat(transactions.get(1).getAmount(), is(equalTo(expense)));
    }

    @Test(expected = InsufficientMoneyException.class)
    public void testAddExpenseWithoutEnoughBalance() throws InvalidMoneyException, InsufficientMoneyException {
        Money expense = Money.createMoney(RUPEE, 500);
        account.addExpense(expense, "Groceries", "Food");
    }

    @Test(expected = InsufficientMoneyException.class)
    public void testWithdrawMoreThanBalance() throws InvalidMoneyException, InsufficientMoneyException {
        Money withdrawAmount = Money.createMoney(RUPEE, 1000);
        account.withdraw(withdrawAmount);
    }

    @Test
    public void testDepositAndWithdraw() throws InvalidMoneyException, InsufficientMoneyException {
        Money depositAmount = Money.createMoney(RUPEE, 2000);
        Money withdrawAmount = Money.createMoney(RUPEE, 1000);

        account.deposit(depositAmount);
        account.withdraw(withdrawAmount);

        assertThat(account.getTransactionHistory().size(), is(0)); // Assuming manual withdrawal and deposit don’t create transactions.
    }

    @Test
    public void testTransactionHistory() throws InvalidMoneyException, InsufficientMoneyException {
        Money income = Money.createMoney(RUPEE, 2000);
        Money expense = Money.createMoney(RUPEE, 1000);

        account.addIncome(income, "Freelancing", "Client");
        account.addExpense(expense, "Shopping", "Leisure");

        List<Transaction> transactions = account.getTransactionHistory();

        assertThat(transactions.size(), is(2));
        assertThat(transactions.get(0).getDescription(), is("Freelancing"));
        assertThat(transactions.get(1).getDescription(), is("Shopping"));
    }
}
