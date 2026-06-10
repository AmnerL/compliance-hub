package com.portafolio.compliancehub.ai.application.internal.outboundservices.embeddings;

import java.util.List;

public interface EmbeddingService {
    List<float[]> embed(List<String> texts);
}
