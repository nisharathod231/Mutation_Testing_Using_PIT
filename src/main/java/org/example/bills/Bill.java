package org.example.bills;

import org.example.models.Money;
import org.example.recurring.RecurrencePattern;

import java.time.LocalDate;

class Bill {
    private final String name;
    private final Money amount;
    private final LocalDate dueDate;
    private final String category;
    private boolean isPaid;
    private LocalDate paidDate;
    private final boolean isRecurring;
    private final RecurrencePattern recurrencePattern;

    public Bill(String name, Money amount, LocalDate dueDate, String category,
                boolean isRecurring, RecurrencePattern recurrencePattern) {
        this.name = name;
        this.amount = amount;
        this.dueDate = dueDate;
        this.category = category;
        this.isPaid = false;
        this.isRecurring = isRecurring;
        this.recurrencePattern = recurrencePattern;
    }

    public void markAsPaid() {
        this.isPaid = true;
        this.paidDate = LocalDate.now();
    }

    public boolean isOverdue() {
        return !isPaid && LocalDate.now().isAfter(dueDate);
    }

    public int getDaysUntilDue() {
        return LocalDate.now().until(dueDate).getDays();
    }

    public Bill generateNextBill() {
        if (!isRecurring) return null;

        LocalDate nextDueDate = null;

        switch (recurrencePattern) {
            case DAILY:
                nextDueDate = dueDate.plusDays(1);
            case WEEKLY:
                nextDueDate = dueDate.plusWeeks(1);
            case MONTHLY:
                nextDueDate = dueDate.plusMonths(1);
            case YEARLY:
                nextDueDate = dueDate.plusYears(1);
        };

        return new Bill(name, amount, nextDueDate, category, true, recurrencePattern);
    }

    public String getName() {
        return name;
    }

    public boolean isPaid() {
        return isPaid;
    }
}
