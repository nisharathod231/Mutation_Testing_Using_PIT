package org.example.bills;

import org.example.notifications.NotificationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

class BillManager {
    private final List<Bill> bills;
    private final NotificationService notificationService;

    public BillManager(NotificationService notificationService) {
        this.bills = new ArrayList<>();
        this.notificationService = notificationService;
    }

    public void addBill(Bill bill) {
        bills.add(bill);
    }

    public List<Bill> getOverdueBills() {
        return bills.stream()
                .filter(Bill::isOverdue)
                .collect(Collectors.toList());
    }

    public List<Bill> getUpcomingBills(int days) {
        return bills.stream()
                .filter(bill -> !bill.isPaid())
                .filter(bill -> bill.getDaysUntilDue() <= days)
                .collect(Collectors.toList());
    }

    public void processRecurringBills() {
        List<Bill> newBills = bills.stream()
                .filter(Bill::isPaid)
                .map(Bill::generateNextBill)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        bills.addAll(newBills);
    }

    public void sendBillReminders() {
        getUpcomingBills(3).forEach(bill ->
                notificationService.notify("Reminder: " + bill.getName() +
                        " due in " + bill.getDaysUntilDue() + " days"));
    }
}
