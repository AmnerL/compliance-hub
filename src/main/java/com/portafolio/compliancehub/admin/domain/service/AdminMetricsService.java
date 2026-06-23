package com.portafolio.compliancehub.admin.domain.service;

public interface AdminMetricsService {
    long getRecentRegulationsCount();
    long getRecentChecklistsCount();
    long getRecentAIConsultationsCount();
}
