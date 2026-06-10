package com.portafolio.compliancehub.ai.domain.model.queries;

import org.springframework.data.domain.Pageable;

public record GetConsultationHistoryQuery(Long userId, Pageable pageable) {

}
