package com.portafolio.compliancehub.report.application.internal;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.portafolio.compliancehub.checklist.infrastructure.persistence.jpa.repositories.ChecklistRepository;
import com.portafolio.compliancehub.regulation.domain.model.aggregates.Regulation;
import com.portafolio.compliancehub.regulation.infrastructure.persistence.jpa.repositories.RegulationRepository;
import com.portafolio.compliancehub.report.domain.model.valueobjects.ConsolidatedEntry;
import com.portafolio.compliancehub.report.domain.model.exceptions.ReportGenerationException;
import com.portafolio.compliancehub.report.domain.service.ConsolidatedReportService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConsolidatedReportServiceImpl implements ConsolidatedReportService {

    private final RegulationRepository regulationRepository;
    private final ChecklistRepository checklistRepository;
    private final TemplateEngine templateEngine;

    @Override
    public byte[] generateConsolidatedReport() {
        List<Regulation> regulations = regulationRepository.findAll();
        List<ConsolidatedEntry> entries = new ArrayList<>();

        for (Regulation reg : regulations) {
            var checklistOpt = checklistRepository.findByRegulationId(reg.getId());
            double progress = 0.0;
            if (checklistOpt.isPresent()) {
                progress = checklistOpt.get().getProgressPercentage();
            }

            String statusColor;
            if (progress >= 80.0) {
                statusColor = "VERDE";
            } else if (progress >= 50.0) {
                statusColor = "AMARILLO";
            } else {
                statusColor = "ROJO";
            }

            entries.add(new ConsolidatedEntry(
                    reg.getTitle(),
                    reg.getIssuingEntity().name(),
                    reg.getCategory().name(),
                    progress,
                    statusColor));
        }

        Context context = new Context();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        context.setVariable("generationDate", LocalDateTime.now().format(formatter));
        context.setVariable("reports", entries);

        String html = templateEngine.process("reports/consolidated_report", context);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new ReportGenerationException("Error generating consolidated PDF report", e);
        }
    }
}
