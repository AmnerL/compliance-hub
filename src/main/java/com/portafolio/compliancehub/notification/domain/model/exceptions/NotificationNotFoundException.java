package com.portafolio.compliancehub.notification.domain.model.exceptions;

public class NotificationNotFoundException extends RuntimeException {
    public NotificationNotFoundException(Long notificationId) {
        super("Notification with ID " + notificationId + " not found.");
    }
}
