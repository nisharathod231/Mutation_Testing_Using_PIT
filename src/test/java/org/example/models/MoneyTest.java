package org.example.models;

import org.example.exceptions.InvalidMoneyException;
import org.example.models.Currency;
import org.example.models.Money;
import org.junit.Test;

import static org.example.models.Money.createMoney;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

public class MoneyTest {

    @Test
    public void convertRupeeToDollar() throws InvalidMoneyException {
        Money oneRupee = createMoney(Currency.RUPEE, 1);
        Money oneRupeeInDollars = createMoney(Currency.DOLLAR, 0.0133);

        assertThat(oneRupee.convertTo(Currency.DOLLAR), is(equalTo(oneRupeeInDollars)));
    }

    @Test
    public void convertRupeeToRupee() throws InvalidMoneyException {
        Money oneRupee = createMoney(Currency.RUPEE, 1);

        assertThat(oneRupee.convertTo(Currency.RUPEE), is(equalTo(oneRupee)));
    }

    @Test
    public void convertDollarToRupee() throws InvalidMoneyException {
        Money oneDollar = createMoney(Currency.DOLLAR, 1);
        Money oneDollarInRupees = createMoney(Currency.RUPEE, 74.85);

        assertThat(oneDollar.convertTo(Currency.RUPEE), is(equalTo(oneDollarInRupees)));
    }

    @Test
    public void convertDollarToDollar() throws InvalidMoneyException {
        Money oneDollar = createMoney(Currency.DOLLAR, 74.85);

        assertThat(oneDollar.convertTo(Currency.DOLLAR), is(equalTo(oneDollar)));
    }

    @Test
    public void addRupeeToDollar() throws InvalidMoneyException {
        Money oneRupee = createMoney(Currency.RUPEE, 1);
        Money oneDollar = createMoney(Currency.DOLLAR, 1);

        Money sum = oneRupee.add(oneDollar);

        assertThat(sum, is(equalTo(createMoney(Currency.RUPEE, 75.85))));
    }

    @Test
    public void addRupeeToRupee() throws InvalidMoneyException {
        Money oneRupee = createMoney(Currency.RUPEE, 1);
        Money tenRupee = createMoney(Currency.RUPEE, 10);

        Money sum = oneRupee.add(tenRupee);

        assertThat(sum, is(equalTo(createMoney(Currency.RUPEE, 11))));
    }

    @Test
    public void addDollarToRupee() throws InvalidMoneyException {
        Money oneRupee = createMoney(Currency.RUPEE, 1);
        Money oneDollar = createMoney(Currency.DOLLAR, 1);

        Money sum = oneDollar.add(oneRupee);

        assertThat(sum, is(equalTo(createMoney(Currency.DOLLAR, 1.0133))));
    }

    @Test
    public void shouldNotBeAbleToCreateNegativeValuedMoney() {
        assertThrows(InvalidMoneyException.class, () -> createMoney(Currency.RUPEE, -5));
    }

    @Test
    public void shouldNotBeAbleToCreateZeroValuedMoney() {
        assertThrows(InvalidMoneyException.class, () -> createMoney(Currency.RUPEE, 0));
    }

    @Test
    public void shouldThrowExceptionWhileCreatingZeroValuedMoney() {
        assertThrows(InvalidMoneyException.class, () -> createMoney(Currency.RUPEE, 0));
    }

    @Test
    public void shouldGiveTrueWhenOneRupeeIsLessThanTwoRupee() throws InvalidMoneyException {
        Money oneRupee = createMoney(Currency.RUPEE, 1);
        Money twoRupee = createMoney(Currency.RUPEE, 2);

        assertTrue(oneRupee.isLessThan(twoRupee));
    }

}
