package com.portafolio.compliancehub.report.domain.model.valueobjects;

public record ConsolidatedEntry(
        String title,
        String issuingEntity,
        String category,
        double progressPercentage,
        String status
) {}
