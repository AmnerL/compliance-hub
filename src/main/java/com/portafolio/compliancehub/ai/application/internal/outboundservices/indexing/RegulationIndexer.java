package com.portafolio.compliancehub.ai.application.internal.outboundservices.indexing;

public interface RegulationIndexer {

    void index(Long regulationId, String fileName, byte[] pdfContent);
}
