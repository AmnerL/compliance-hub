package com.portafolio.compliancehub.notification.domain.model.aggregates;

import com.portafolio.compliancehub.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "notifications")
@Entity
@NoArgsConstructor
public class Notification extends AuditableAbstractAggregateRoot<Notification> {

    private Long userId;

    private Long regulationId;

    private String message;

    private Boolean read;

    public Notification(Long userId, Long regulationId, String message) {
        this.userId = userId;
        this.regulationId = regulationId;
        this.message = message;
        this.read = false;
    }

    public void markAsRead() {
        this.read = true;
    }
}
