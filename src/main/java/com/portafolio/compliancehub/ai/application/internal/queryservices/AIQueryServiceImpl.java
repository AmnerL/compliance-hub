package com.portafolio.compliancehub.ai.application.internal.queryservices;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.portafolio.compliancehub.ai.domain.model.aggregates.Consultation;
import com.portafolio.compliancehub.ai.domain.model.queries.GetConsultationHistoryQuery;
import com.portafolio.compliancehub.ai.domain.model.queries.GetConsultationQuery;
import com.portafolio.compliancehub.ai.domain.service.AIQueryService;
import com.portafolio.compliancehub.ai.infrastructure.persistence.jpa.repositories.ConsultationRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AIQueryServiceImpl implements AIQueryService {

    private final ConsultationRepository consultationRepository;

    @Override
    public Consultation handle(GetConsultationQuery query) {
        return consultationRepository.findById(query.id())
                .orElseThrow(() -> new RuntimeException("Consultation not found"));
    }

    @Override
    public Page<Consultation> handle(GetConsultationHistoryQuery query) {
        return consultationRepository.findByUserId(query.userId(), query.pageable());
    }

}
