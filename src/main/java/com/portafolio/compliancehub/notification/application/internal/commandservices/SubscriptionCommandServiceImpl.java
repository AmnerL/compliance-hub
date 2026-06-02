package com.portafolio.compliancehub.notification.application.internal.commandservices;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portafolio.compliancehub.notification.domain.model.aggregates.Subscription;
import com.portafolio.compliancehub.notification.domain.model.commands.*;
import com.portafolio.compliancehub.notification.domain.service.SubscriptionCommandService;
import com.portafolio.compliancehub.notification.infrastructure.persistence.jpa.repositories.SubscriptionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SubscriptionCommandServiceImpl implements SubscriptionCommandService {

    private final SubscriptionRepository subscriptionRepository;

    @Override
    public Subscription handle(SubscribeCommand command) {
        var subscription = new Subscription(command.userId(), command.category());
        return subscriptionRepository.save(subscription);
    }

    @Transactional
    @Override
    public void handle(UnsubscribeCommand command) {
        subscriptionRepository.deleteByUserIdAndCategory(command.userId(), command.category());
    }

}
