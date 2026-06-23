package com.portafolio.compliancehub.admin.interfaces.rest.resources;

public record MetricResponse(
    long regulationsCount,
    long checklistsCount,
    long aiConsultationsCount
) {}
