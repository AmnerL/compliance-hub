package com.portafolio.compliancehub.ai.domain.service;

import com.portafolio.compliancehub.ai.domain.model.aggregates.Consultation;
import com.portafolio.compliancehub.ai.domain.model.commands.ConsultCommand;

public interface AICommandService {
    Consultation handle(ConsultCommand command);

    void updateFeedback(Long consultationId, boolean useful);
}
