package com.portafolio.compliancehub.regulation.application.internal.outboundservices.storage;

import java.io.InputStream;

public interface FileStorageService {
    String upload(String key, InputStream inputStream, long contentLenght);

    InputStream download(String key);

    void delete(String key);
}
