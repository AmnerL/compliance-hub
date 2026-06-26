package com.portafolio.compliancehub.checklist.infrastructure.persistence.jpa.repositories;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portafolio.compliancehub.checklist.domain.model.aggregates.Checklist;

@Repository
public interface ChecklistRepository extends JpaRepository<Checklist, Long> {
    long countByCreatedAtAfter(LocalDateTime dateTime);
    Optional<Checklist> findByRegulationId(Long regulationId);
}
