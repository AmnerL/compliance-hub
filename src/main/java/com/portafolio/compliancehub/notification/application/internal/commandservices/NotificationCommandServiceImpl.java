package com.portafolio.compliancehub.notification.application.internal.commandservices;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portafolio.compliancehub.notification.domain.model.aggregates.Notification;
import com.portafolio.compliancehub.notification.domain.model.commands.MarkAsReadCommand;
import com.portafolio.compliancehub.notification.domain.service.NotificationCommandService;
import com.portafolio.compliancehub.notification.infrastructure.persistence.jpa.repositories.NotificationRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NotificationCommandServiceImpl implements NotificationCommandService {

    private final NotificationRepository notificationRepository;

    @Transactional
    @Override
    public Notification handle(MarkAsReadCommand command) {
        var notification = notificationRepository.findById(command.notificationId())
                .orElseThrow(() -> new IllegalArgumentException("Notification not found"));
        notification.markAsRead();
        return notificationRepository.save(notification);
    }

}
