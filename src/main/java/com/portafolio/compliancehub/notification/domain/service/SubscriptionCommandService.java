package com.portafolio.compliancehub.notification.domain.service;

import com.portafolio.compliancehub.notification.domain.model.aggregates.Subscription;
import com.portafolio.compliancehub.notification.domain.model.commands.*;

public interface SubscriptionCommandService {
    Subscription handle(SubscribeCommand command);

    void handle(UnsubscribeCommand command);
}
