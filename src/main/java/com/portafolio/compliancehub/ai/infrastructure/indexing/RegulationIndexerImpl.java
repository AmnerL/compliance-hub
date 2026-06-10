package com.portafolio.compliancehub.ai.infrastructure.indexing;

import com.portafolio.compliancehub.ai.application.internal.outboundservices.indexing.RegulationIndexer;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegulationIndexerImpl implements RegulationIndexer {

    private final VectorStore vectorStore;

    @Async
    @Override
    public void index(Long regulationId, String fileName, byte[] pdfContent) {
        var resource = new ByteArrayResource(pdfContent);
        var pdfReader = new PagePdfDocumentReader(resource);

        List<Document> rawDocuments = pdfReader.get();
        var tokenSplitter = new TokenTextSplitter();
        List<Document> rawChunks = tokenSplitter.apply(rawDocuments);

        List<Document> chunksWithMetadata = rawChunks.stream().map(chunk -> {
            chunk.getMetadata().put("regulationId", regulationId.toString());
            chunk.getMetadata().put("fileName", fileName);
            chunk.getMetadata().put("pageNumber", chunk.getMetadata().getOrDefault("page", "unknown").toString());
            return chunk;
        }).toList();

        vectorStore.accept(chunksWithMetadata);
    }
}
