package org.example.goals;

import org.example.exceptions.InsufficientMoneyException;
import org.example.exceptions.InvalidMoneyException;
import org.example.models.Currency;
import org.example.models.Money;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class FinancialGoalTest {

    private FinancialGoal financialGoal;

    @Before
    public void setup() throws InvalidMoneyException {
        Money targetAmount = Money.createMoney(Currency.RUPEE, 10000);
        LocalDate targetDate = LocalDate.now().plusDays(60);
        financialGoal = new FinancialGoal("Vacation", targetAmount, targetDate, "Leisure");
    }

    @Test
    public void testGetRemainingAmount() throws InsufficientMoneyException, InvalidMoneyException {
        Money contribution = Money.createMoney(Currency.RUPEE, 4000);
        financialGoal.addContribution(contribution);

        Money remainingAmount = financialGoal.getRemainingAmount();

        assertThat(remainingAmount.getValue(), is(6000.0));
        assertThat(remainingAmount.getCurrency(), is(Currency.RUPEE));
    }
}
