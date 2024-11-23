package org.example.models;

import org.example.exceptions.InsufficientMoneyException;
import org.example.exceptions.InvalidMoneyException;

public class Money {
    private final Currency currency;
    private final double value;

    public Money(Currency currency, double value) {
        this.currency = currency;
        this.value = value;
    }

    public static Money createMoney(Currency currency, double value) throws InvalidMoneyException {
        if (value <= 0) {
            throw new InvalidMoneyException(value);
        }
        return new Money(currency, value);
    }

    public Money add(Money money) {
        Money convertedMoney = money.convertTo(this.currency);

        return new Money(this.currency, this.value + convertedMoney.value);
    }

    public Money subtract(Money money) throws InsufficientMoneyException {
        Money convertedMoney = money.convertTo(this.currency);

        if (this.isLessThan(convertedMoney)) throw new InsufficientMoneyException();

        return new Money(money.currency, this.value - convertedMoney.value);
    }

    public boolean isLessThan(Money money) {
        return this.value < money.currency.convertTo(this.currency, money.value);
    }

    public Money convertTo(Currency currency) {
        return new Money(currency, this.currency.convertTo(currency, this.value));
    }

    @Override
    public String toString() {
        return this.currency + " " + this.value;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        Money otherMoney = (Money) obj;
        return this.currency == otherMoney.currency && Math.abs(this.value - otherMoney.value) < 0.01;
    }


    public Currency getCurrency() {
        return currency;
    }

    public double getValue() {
        return value;
    }
}
