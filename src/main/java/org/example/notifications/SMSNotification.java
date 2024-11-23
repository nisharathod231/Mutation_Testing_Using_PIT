package org.example.notifications;

public class SMSNotification implements NotificationStrategy {
    private final String phoneNumber;

    public SMSNotification(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void sendNotification(String message) {
        // Implementation for sending SMS notifications
        System.out.println("Sending SMS to " + phoneNumber + ": " + message);
    }
}
