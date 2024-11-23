package org.example.exceptions;

public class InsufficientMoneyException extends Throwable {
    @Override
    public String toString() {
        return "Insufficient Money in the Wallet";
    }
}
