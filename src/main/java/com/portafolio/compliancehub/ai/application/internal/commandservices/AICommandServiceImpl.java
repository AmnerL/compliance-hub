package com.portafolio.compliancehub.ai.application.internal.commandservices;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portafolio.compliancehub.ai.application.internal.outboundservices.llm.LLMService;
import com.portafolio.compliancehub.ai.domain.model.aggregates.Consultation;
import com.portafolio.compliancehub.ai.domain.model.commands.ConsultCommand;
import com.portafolio.compliancehub.ai.domain.model.valueobjects.Source;
import com.portafolio.compliancehub.ai.domain.service.AICommandService;
import com.portafolio.compliancehub.ai.infrastructure.persistence.jpa.repositories.ConsultationRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AICommandServiceImpl implements AICommandService {

        private final ConsultationRepository consultationRepository;
        private final VectorStore vectorStore;
        private final LLMService llmService;

        @Override
        @Transactional
        public Consultation handle(ConsultCommand command) {
                SearchRequest searchRequest = SearchRequest.builder()
                                .query(command.question())
                                .topK(3)
                                .similarityThreshold(0.7)
                                .build();

                List<Document> similarDocuments = vectorStore.similaritySearch(searchRequest);

                String context = similarDocuments.stream()
                                .map(Document::getText)
                                .collect(Collectors.joining("\n\n"));
                List<Source> sources = similarDocuments.stream()
                                .map(doc -> {
                                        String title = (String) doc.getMetadata().getOrDefault("fileName",
                                                        "Documento desconocido");
                                        String pageStr = (String) doc.getMetadata().get("pageNumber");

                                        Integer pageNumber = null;
                                        try {
                                                if (pageStr != null && !pageStr.equals("unknown")) {
                                                        pageNumber = Integer.parseInt(pageStr);
                                                }
                                        } catch (NumberFormatException e) {

                                        }

                                        String excerpt = doc.getText();

                                        return new Source(title, pageNumber, excerpt);
                                })
                                .toList();

                String finalPrompt = String.format(
                                "Eres un asistente experto en cumplimiento legal y normativas internas.\n" +
                                                "Responde a la siguiente consulta basándote únicamente en el contexto proporcionado abajo.\n"
                                                +
                                                "Si el contexto no contiene la información para responder, di amablemente que no posees información oficial al respecto.\n\n"
                                                +
                                                "=== CONTEXTO DE CUMPLIMIENTO ===\n%s\n=================================\n\n"
                                                +
                                                "PREGUNTA DEL USUARIO: %s\n\n" +
                                                "RESPUESTA:",
                                context, command.question());

                String llmResponse = llmService.generate(finalPrompt);

                Consultation consultation = new Consultation(
                                command.userId(),
                                command.question(),
                                llmResponse,
                                sources);

                return consultationRepository.save(consultation);
        }

        @Override
        @Transactional
        public void updateFeedback(Long consultationId, boolean useful) {
                Consultation consultation = consultationRepository.findById(consultationId)
                                .orElseThrow(() -> new RuntimeException("Consultation not found"));
                consultation.registerFeedback(useful);
                consultationRepository.save(consultation);
        }

}
