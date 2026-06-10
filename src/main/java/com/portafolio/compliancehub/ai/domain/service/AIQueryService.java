package com.portafolio.compliancehub.ai.domain.service;

import org.springframework.data.domain.Page;

import com.portafolio.compliancehub.ai.domain.model.aggregates.Consultation;
import com.portafolio.compliancehub.ai.domain.model.queries.GetConsultationHistoryQuery;
import com.portafolio.compliancehub.ai.domain.model.queries.GetConsultationQuery;

public interface AIQueryService {
    Consultation handle(GetConsultationQuery query);

    Page<Consultation> handle(GetConsultationHistoryQuery query);
}
