package org.example.analytics;

import org.example.models.Currency;
import org.example.models.Money;
import org.example.transactions.ExpenseTransaction;
import org.example.transactions.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class SpendingAnalytics {
    private final List<Transaction> transactions;

    public SpendingAnalytics(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Map<String, Double> calculateCategoryPercentages() {
        Map<String, Money> categoryTotals = transactions.stream()
                .filter(t -> t instanceof ExpenseTransaction)
                .map(t -> (ExpenseTransaction) t)
                .collect(Collectors.groupingBy(
                        ExpenseTransaction::getCategory,
                        Collectors.reducing(
                                new Money(Currency.RUPEE, 0),
                                Transaction::getAmount,
                                Money::add
                        )
                ));

        Money totalExpenses = categoryTotals.values().stream()
                .reduce(new Money(Currency.RUPEE, 0), Money::add);

        Map<String, Double> percentages = new HashMap<>();
        categoryTotals.forEach((category, amount) -> {
            double percentage = (amount.getValue() / totalExpenses.getValue()) * 100;
            percentages.put(category, Math.round(percentage * 100.0) / 100.0);
        });

        return percentages;
    }

    public List<Transaction> getUnusualTransactions(double threshold) {
        Map<String, List<Money>> categoryAmounts = transactions.stream()
                .filter(t -> t instanceof ExpenseTransaction)
                .map(t -> (ExpenseTransaction) t)
                .collect(Collectors.groupingBy(
                        ExpenseTransaction::getCategory,
                        Collectors.mapping(
                                Transaction::getAmount,
                                Collectors.toList()
                        )
                ));

        List<Transaction> unusualTransactions = new ArrayList<>();
        categoryAmounts.forEach((category, amounts) -> {
            double average = amounts.stream()
                    .mapToDouble(Money::getValue)
                    .average()
                    .orElse(0);

            double stdDev = calculateStandardDeviation(amounts, average);

            transactions.stream()
                    .filter(t -> t instanceof ExpenseTransaction)
                    .map(t -> (ExpenseTransaction) t)
                    .filter(t -> t.getCategory().equals(category))
                    .filter(t -> Math.abs(t.getAmount().getValue() - average) > threshold * stdDev)
                    .forEach(unusualTransactions::add);
        });

        return unusualTransactions;
    }

    private double calculateStandardDeviation(List<Money> amounts, double mean) {
        double variance = amounts.stream()
                .mapToDouble(amount -> {
                    double diff = amount.getValue() - mean;
                    return diff * diff;
                })
                .average()
                .orElse(0);
        return Math.sqrt(variance);
    }
}
