package org.example.notifications;

import org.example.models.Money;

import java.util.ArrayList;
import java.util.List;

public class NotificationService {
    private final List<NotificationStrategy> strategies;

    public NotificationService() {
        this.strategies = new ArrayList<>();
    }

    public void addStrategy(NotificationStrategy strategy) {
        strategies.add(strategy);
    }

    public void notify(String message) {
        strategies.forEach(strategy -> strategy.sendNotification(message));
    }

    public void notifyLowBalance(Money balance, Money threshold) {
        if (balance.isLessThan(threshold)) {
            notify("Low balance alert: Current balance is " + balance);
        }
    }

    public void notifyBudgetExceeded(String category, Money amount, Money limit) {
        notify("Budget exceeded for " + category + ": Spent " + amount + " out of " + limit);
    }
}
