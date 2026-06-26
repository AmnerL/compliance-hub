package com.portafolio.compliancehub.report.interfaces.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portafolio.compliancehub.report.domain.service.ConsolidatedReportService;
import com.portafolio.compliancehub.report.domain.service.RegulationReportService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/reports")
@PreAuthorize("hasAnyRole('ADMIN', 'OFFICER')")
@AllArgsConstructor
public class ReportController {

    private final RegulationReportService regulationReportService;
    private final ConsolidatedReportService consolidatedReportService;

    @GetMapping("/regulation/{id}")
    public ResponseEntity<byte[]> getRegulationReport(@PathVariable Long id) {
        byte[] pdfBytes = regulationReportService.generateReport(id);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"regulation_report_" + id + ".pdf\"");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }

    @GetMapping("/consolidated")
    public ResponseEntity<byte[]> getConsolidatedReport() {
        byte[] pdfBytes = consolidatedReportService.generateConsolidatedReport();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"consolidated_report.pdf\"");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }
}
