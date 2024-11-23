package org.example.recurring;

import org.example.transactions.ExpenseTransaction;
import org.example.transactions.IncomeTransaction;
import org.example.transactions.Transaction;

import java.time.LocalDate;

class RecurringTransaction {
    private final Transaction baseTransaction;
    private final RecurrencePattern pattern;
    private final LocalDate startDate;
    private LocalDate endDate;
    private final int maxOccurrences;
    private int currentOccurrences;

    public RecurringTransaction(Transaction baseTransaction, RecurrencePattern pattern,
                                LocalDate startDate, LocalDate endDate, int maxOccurrences) {
        this.baseTransaction = baseTransaction;
        this.pattern = pattern;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxOccurrences = maxOccurrences;
        this.currentOccurrences = 0;
    }

    public boolean shouldExecute(LocalDate currentDate) {
        if (currentOccurrences >= maxOccurrences) return false;
        if (endDate != null && currentDate.isAfter(endDate)) return false;
        return pattern.matches(startDate, currentDate);
    }

    public Transaction generateTransaction(LocalDate currentDate) {
        if (!shouldExecute(currentDate)) return null;
        currentOccurrences++;

        switch (baseTransaction.getType()) {
            case INCOME:
                IncomeTransaction incomeBase = (IncomeTransaction) baseTransaction;
                return new IncomeTransaction(
                        incomeBase.getAmount(),
                        incomeBase.getDescription(),
                        currentDate.toString(),
                        incomeBase.getSource()
                );
            case EXPENSE:
                ExpenseTransaction expenseBase = (ExpenseTransaction) baseTransaction;
                return new ExpenseTransaction(
                        expenseBase.getAmount(),
                        expenseBase.getDescription(),
                        currentDate.toString(),
                        expenseBase.getCategory()
                );
            default:
                return null;
        }
    }
}
