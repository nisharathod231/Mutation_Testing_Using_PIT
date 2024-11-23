package org.example.reports;

import org.example.models.Currency;
import org.example.models.Money;
import org.example.transactions.ExpenseTransaction;
import org.example.transactions.IncomeTransaction;
import org.example.transactions.Transaction;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class FinancialReport {
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final List<Transaction> transactions;
    private final Currency reportCurrency;

    public FinancialReport(LocalDate startDate, LocalDate endDate, List<Transaction> transactions, Currency reportCurrency) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.transactions = transactions;
        this.reportCurrency = reportCurrency;
    }

    public Map<String, Money> getCategoryTotals() {
        return transactions.stream()
                .filter(t -> t instanceof ExpenseTransaction)
                .map(t -> (ExpenseTransaction) t)
                .collect(Collectors.groupingBy(
                        ExpenseTransaction::getCategory,
                        Collectors.reducing(
                                new Money(reportCurrency, 0),
                                Transaction::getAmount,
                                Money::add
                        )
                ));
    }

    public Map<String, Money> getIncomeBySource() {
        return transactions.stream()
                .filter(t -> t instanceof IncomeTransaction)
                .map(t -> (IncomeTransaction) t)
                .collect(Collectors.groupingBy(
                        IncomeTransaction::getSource,
                        Collectors.reducing(
                                new Money(reportCurrency, 0),
                                Transaction::getAmount,
                                Money::add
                        )
                ));
    }

    public String generateSummaryReport() {
        StringBuilder report = new StringBuilder();
        report.append("Financial Report\n");
        report.append("Period: ").append(startDate).append(" to ").append(endDate).append("\n\n");

        report.append("Income by Source:\n");
        getIncomeBySource().forEach((source, amount) ->
                report.append(String.format("- %s: %s\n", source, amount)));

        report.append("\nExpenses by Category:\n");
        getCategoryTotals().forEach((category, amount) ->
                report.append(String.format("- %s: %s\n", category, amount)));

        return report.toString();
    }
}
