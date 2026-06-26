package com.portafolio.compliancehub.report.domain.service;

public interface RegulationReportService {
    byte[] generateReport(Long regulationId);
}
