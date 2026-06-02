package com.portafolio.compliancehub.notification.domain.model.aggregates;

import com.portafolio.compliancehub.regulation.domain.model.valueobjects.Category;
import com.portafolio.compliancehub.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "subscriptions")
@Entity
@NoArgsConstructor
public class Subscription extends AuditableAbstractAggregateRoot<Subscription> {

    private Long userId;

    @Enumerated(EnumType.STRING)
    private Category category;

    public Subscription(Long userId, Category category) {
        this.userId = userId;
        this.category = category;
    }

}
