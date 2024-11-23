package org.example.exceptions;

public class InvalidMoneyException extends Throwable {
    private final double value;

    public InvalidMoneyException(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "You cannot create money with " + value  + " amount";
    }
}
