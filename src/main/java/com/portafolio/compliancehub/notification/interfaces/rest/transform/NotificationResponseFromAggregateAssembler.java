package com.portafolio.compliancehub.notification.interfaces.rest.transform;

import com.portafolio.compliancehub.notification.domain.model.aggregates.Notification;
import com.portafolio.compliancehub.notification.interfaces.rest.resources.NotificationResponseResource;

public class NotificationResponseFromAggregateAssembler {

    public static NotificationResponseResource toResource(Notification notification) {
        return new NotificationResponseResource(notification.getId(), notification.getMessage(),
                notification.getRead());
    }

}
