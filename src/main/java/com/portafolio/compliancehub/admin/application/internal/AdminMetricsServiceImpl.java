package com.portafolio.compliancehub.admin.application.internal;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portafolio.compliancehub.admin.domain.service.AdminMetricsService;
import com.portafolio.compliancehub.ai.infrastructure.persistence.jpa.repositories.ConsultationRepository;
import com.portafolio.compliancehub.checklist.infrastructure.persistence.jpa.repositories.ChecklistRepository;
import com.portafolio.compliancehub.regulation.infrastructure.persistence.jpa.repositories.RegulationRepository;

@Service
public class AdminMetricsServiceImpl implements AdminMetricsService {

    private final RegulationRepository regulationRepository;
    private final ChecklistRepository checklistRepository;
    private final ConsultationRepository consultationRepository;

    public AdminMetricsServiceImpl(
            RegulationRepository regulationRepository,
            ChecklistRepository checklistRepository,
            ConsultationRepository consultationRepository) {
        this.regulationRepository = regulationRepository;
        this.checklistRepository = checklistRepository;
        this.consultationRepository = consultationRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public long getRecentRegulationsCount() {
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        return regulationRepository.countByCreatedAtAfter(thirtyDaysAgo);
    }

    @Override
    @Transactional(readOnly = true)
    public long getRecentChecklistsCount() {
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        return checklistRepository.countByCreatedAtAfter(thirtyDaysAgo);
    }

    @Override
    @Transactional(readOnly = true)
    public long getRecentAIConsultationsCount() {
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        return consultationRepository.countByCreatedAtAfter(thirtyDaysAgo);
    }
}
