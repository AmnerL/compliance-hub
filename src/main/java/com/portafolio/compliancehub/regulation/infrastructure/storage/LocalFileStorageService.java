package com.portafolio.compliancehub.regulation.infrastructure.storage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.portafolio.compliancehub.regulation.application.internal.outboundservices.storage.FileStorageService;

@Service
@Profile("local")
public class LocalFileStorageService implements FileStorageService {

    private final Path basePath;

    public LocalFileStorageService(@Value("${app.storage.local.base-dir:./storage}") String baseDir) {
        this.basePath = Paths.get(baseDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(basePath);
        } catch (IOException e) {
            throw new RuntimeException("Could not create storage directory: " + basePath, e);
        }
    }

    @Override
    public String upload(String key, InputStream inputStream, long contentLength) {
        try {
            Path targetPath = resolvePath(key);
            Files.createDirectories(targetPath.getParent());
            Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
            return key;
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file: " + key, e);
        }
    }

    @Override
    public InputStream download(String key) {
        try {
            Path filePath = resolvePath(key);
            return Files.newInputStream(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to download file: " + key, e);
        }
    }

    @Override
    public void delete(String key) {
        try {
            Path filePath = resolvePath(key);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file: " + key, e);
        }
    }

    private Path resolvePath(String key) {
        Path resolved = basePath.resolve(key).normalize();
        if (!resolved.startsWith(basePath)) {
            throw new SecurityException("Invalid file path: " + key);
        }
        return resolved;
    }
}
