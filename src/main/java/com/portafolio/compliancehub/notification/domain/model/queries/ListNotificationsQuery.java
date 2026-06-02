package com.portafolio.compliancehub.notification.domain.model.queries;

import org.springframework.data.domain.Pageable;

public record ListNotificationsQuery(Long userId, Boolean unreadOnly, Pageable pageable) {

}
