package com.portafolio.compliancehub.notification.domain.service;

import com.portafolio.compliancehub.notification.domain.model.aggregates.Notification;
import com.portafolio.compliancehub.notification.domain.model.commands.*;

public interface NotificationCommandService {
    Notification handle(MarkAsReadCommand command);
}
