package com.portafolio.compliancehub.ai.infrastructure.embeddings;

import com.portafolio.compliancehub.ai.application.internal.outboundservices.embeddings.EmbeddingService;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class EmbeddingServiceImpl implements EmbeddingService {

    private final EmbeddingModel embeddingModel;

    @Override
    public List<float[]> embed(List<String> texts) {
        return embeddingModel.embed(texts);
    }
}
