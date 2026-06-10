package com.portafolio.compliancehub.ai.interfaces.rest.transform;

import com.portafolio.compliancehub.ai.domain.model.aggregates.Consultation;
import com.portafolio.compliancehub.ai.interfaces.rest.resources.ConsultationResponseResource;

public class ConsultationResponseFromAggregateAssembler {
    public static ConsultationResponseResource fromAggregate(Consultation consultation) {
        var sourceResources = consultation.getSources().stream()
                .map(source -> new ConsultationResponseResource.SourceResource(
                        source.getDocumentTitle(),
                        source.getPageNumber(),
                        source.getExcerpt()))
                .toList();

        return new ConsultationResponseResource(
                consultation.getId(),
                consultation.getQuestion(),
                consultation.getAnswer(),
                sourceResources);
    }
}
