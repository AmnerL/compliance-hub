package com.portafolio.compliancehub.notification.application.internal.queryservice;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.portafolio.compliancehub.notification.domain.model.aggregates.Notification;
import com.portafolio.compliancehub.notification.domain.model.queries.ListNotificationsQuery;
import com.portafolio.compliancehub.notification.domain.service.NotificationQueryService;
import com.portafolio.compliancehub.notification.infrastructure.persistence.jpa.repositories.NotificationRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NotificationQueryServiceImpl implements NotificationQueryService {

    private final NotificationRepository notificationRepository;

    @Override
    public Page<Notification> handle(ListNotificationsQuery query) {
        if (Boolean.TRUE.equals(query.unreadOnly())) {
            return notificationRepository.findByUserIdAndReadFalse(query.userId(), query.pageable());
        }
        return notificationRepository.findByUserId(query.userId(), query.pageable());
    }

}
