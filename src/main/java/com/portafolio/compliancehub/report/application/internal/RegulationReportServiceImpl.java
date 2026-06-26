package com.portafolio.compliancehub.report.application.internal;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.portafolio.compliancehub.checklist.infrastructure.persistence.jpa.repositories.ChecklistRepository;
import com.portafolio.compliancehub.regulation.domain.model.aggregates.Regulation;
import com.portafolio.compliancehub.regulation.domain.model.exceptions.RegulationNotFoundException;
import com.portafolio.compliancehub.regulation.infrastructure.persistence.jpa.repositories.RegulationRepository;
import com.portafolio.compliancehub.report.domain.model.exceptions.ReportGenerationException;
import com.portafolio.compliancehub.report.domain.service.RegulationReportService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegulationReportServiceImpl implements RegulationReportService {

    private final RegulationRepository regulationRepository;
    private final ChecklistRepository checklistRepository;
    private final TemplateEngine templateEngine;

    @Override
    public byte[] generateReport(Long regulationId) {
        Regulation regulation = regulationRepository.findById(regulationId)
                .orElseThrow(() -> new RegulationNotFoundException(regulationId));

        var checklistOpt = checklistRepository.findByRegulationId(regulationId);

        Context context = new Context();
        context.setVariable("regulationTitle", regulation.getTitle());
        context.setVariable("issuingEntity", regulation.getIssuingEntity());
        context.setVariable("categoryName", regulation.getCategory().name());
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        context.setVariable("publicationDate", regulation.getPublicationDate().format(formatter));

        if (checklistOpt.isPresent()) {
            var checklist = checklistOpt.get();
            context.setVariable("progressPercentage", checklist.getProgressPercentage());
            context.setVariable("items", checklist.getItems());
        } else {
            context.setVariable("progressPercentage", 0.0);
            context.setVariable("items", Collections.emptyList());
        }

        String html = templateEngine.process("reports/regulation_report", context);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new ReportGenerationException("Error generating PDF report for regulation ID: " + regulationId, e);
        }
    }
}
