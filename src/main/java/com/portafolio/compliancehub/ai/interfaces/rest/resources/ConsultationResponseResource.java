package com.portafolio.compliancehub.ai.interfaces.rest.resources;

import java.util.List;

public record ConsultationResponseResource(
        Long id,
        String question,
        String answer,
        List<SourceResource> sources) {
    public record SourceResource(
            String documentTitle,
            Integer pageNumber,
            String excerpt) {
    }
}
