package com.portafolio.compliancehub.regulation.interfaces.resource;

import java.time.LocalDate;

import com.portafolio.compliancehub.regulation.domain.model.valueobjects.Category;
import com.portafolio.compliancehub.regulation.domain.model.valueobjects.IssuingEntity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UploadRegulationResource(
        @NotBlank String title,
        @NotNull LocalDate publicationDate,
        @NotNull IssuingEntity issuingEntity,
        @NotNull Category category) {
}
