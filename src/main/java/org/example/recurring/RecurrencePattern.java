package org.example.recurring;

import java.time.LocalDate;

public enum RecurrencePattern {
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY;

    public boolean matches(LocalDate startDate, LocalDate currentDate) {
        switch (this) {
            case DAILY:
                return true;
            case WEEKLY:
                return startDate.getDayOfWeek() == currentDate.getDayOfWeek();
            case MONTHLY:
                return startDate.getDayOfMonth() == currentDate.getDayOfMonth();
            case YEARLY:
                return startDate.getDayOfYear() == currentDate.getDayOfYear();
            default:
                return false;
        }
    }
}
