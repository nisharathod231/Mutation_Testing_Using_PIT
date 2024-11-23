package org.example.notifications;

public class EmailNotification implements NotificationStrategy {
    private final String emailAddress;

    public EmailNotification(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public void sendNotification(String message) {
        // Implementation for sending email notifications
        System.out.println("Sending email to " + emailAddress + ": " + message);
    }
}
