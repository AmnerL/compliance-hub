package com.portafolio.compliancehub.regulation.interfaces.resource;

import java.time.LocalDate;
import java.util.List;

import com.portafolio.compliancehub.regulation.domain.model.valueobjects.Category;
import com.portafolio.compliancehub.regulation.domain.model.valueobjects.IssuingEntity;
import com.portafolio.compliancehub.regulation.domain.model.valueobjects.RegulationStatus;

public record RegulationResponseResource(
        Long id,
        String title,
        LocalDate publicationDate,
        IssuingEntity issuingEntity,
        Category category,
        RegulationStatus status,
        List<VersionResponseResource> versions) {

}
