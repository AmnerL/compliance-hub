package com.portafolio.compliancehub.shared.domain.model.aggregates;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditableAbstractAggregateRoot<T extends AbstractAggregateRoot<T>> extends AbstractAggregateRoot<T> {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Getter
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
