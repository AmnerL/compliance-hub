package com.portafolio.compliancehub.notification.infrastructure.persistence.jpa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portafolio.compliancehub.notification.domain.model.aggregates.Subscription;
import com.portafolio.compliancehub.regulation.domain.model.valueobjects.Category;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    void deleteByUserIdAndCategory(Long userId, Category category);

    List<Subscription> findByCategory(Category category);
}
