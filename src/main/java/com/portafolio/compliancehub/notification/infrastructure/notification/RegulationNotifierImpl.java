package com.portafolio.compliancehub.notification.infrastructure.notification;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.portafolio.compliancehub.notification.application.internal.outboundservices.notification.RegulationNotifier;
import com.portafolio.compliancehub.notification.domain.model.aggregates.Notification;
import com.portafolio.compliancehub.notification.domain.model.aggregates.Subscription;
import com.portafolio.compliancehub.notification.infrastructure.persistence.jpa.repositories.NotificationRepository;
import com.portafolio.compliancehub.notification.infrastructure.persistence.jpa.repositories.SubscriptionRepository;
import com.portafolio.compliancehub.regulation.domain.model.aggregates.Regulation;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RegulationNotifierImpl implements RegulationNotifier {

    private static final Logger log = LoggerFactory.getLogger(RegulationNotifierImpl.class);

    private final SubscriptionRepository subscriptionRepository;
    private final NotificationRepository notificationRepository;

    @Override
    public void notifyNewRegulation(Regulation regulation) {
        List<Subscription> subscriptions = subscriptionRepository.findByCategory(regulation.getCategory());

        for (Subscription sub : subscriptions) {
            String message = String.format("Nueva normativa '%s' de categoría %s", regulation.getTitle(),
                    regulation.getCategory());
            Notification notification = new Notification(
                    sub.getUserId(),
                    regulation.getId(),
                    message);

            notificationRepository.save(notification);
            log.info("Notificación enviada a usuario {}: {}", sub.getUserId(), message);
        }

    }

}
