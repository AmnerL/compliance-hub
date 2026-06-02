package com.portafolio.compliancehub.notification.domain.service;

import org.springframework.data.domain.Page;

import com.portafolio.compliancehub.notification.domain.model.aggregates.Notification;
import com.portafolio.compliancehub.notification.domain.model.queries.ListNotificationsQuery;

public interface NotificationQueryService {
    Page<Notification> handle(ListNotificationsQuery query);
}
