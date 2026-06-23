package com.portafolio.compliancehub.ai.infrastructure.persistence.jpa.repositories;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.portafolio.compliancehub.ai.domain.model.aggregates.Consultation;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    Page<Consultation> findByUserId(Long userId, Pageable pageable);
    long countByCreatedAtAfter(LocalDateTime dateTime);
}
