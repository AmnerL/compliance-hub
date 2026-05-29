package com.portafolio.compliancehub.regulation.application.internal.queryservices;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portafolio.compliancehub.regulation.application.internal.outboundservices.storage.FileStorageService;
import com.portafolio.compliancehub.regulation.domain.model.aggregates.Regulation;
import com.portafolio.compliancehub.regulation.domain.model.entities.RegulationVersion;
import com.portafolio.compliancehub.regulation.domain.model.queries.DownloadRegulationQuery;
import com.portafolio.compliancehub.regulation.domain.model.queries.GetRegulationQuery;
import com.portafolio.compliancehub.regulation.domain.model.queries.ListRegulationsQuery;
import com.portafolio.compliancehub.regulation.domain.service.RegulationQueryService;
import com.portafolio.compliancehub.regulation.domain.model.exceptions.NoActiveVersionException;
import com.portafolio.compliancehub.regulation.domain.model.exceptions.RegulationNotFoundException;
import com.portafolio.compliancehub.regulation.domain.model.exceptions.VersionNotFoundException;
import com.portafolio.compliancehub.regulation.infrastructure.persistence.jpa.repositories.RegulationRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class RegulationQueryServiceImpl implements RegulationQueryService {

    private final RegulationRepository regulationRepository;
    private final FileStorageService fileStorageService;

    @Override
    public Regulation handle(GetRegulationQuery query) {
        return regulationRepository.findById(query.regulationId())
                .orElseThrow(() -> new RegulationNotFoundException(query.regulationId()));
    }

    @Override
    public Page<Regulation> handle(ListRegulationsQuery query) {
        String searchTerm = query.searchTerm();
        return regulationRepository.findByFilters(searchTerm, query.categoryFilter(), query.pageable());
    }

    @Override
    public byte[] handle(DownloadRegulationQuery query) {
        Regulation regulation = regulationRepository.findById(query.regulationId())
                .orElseThrow(() -> new RegulationNotFoundException(query.regulationId()));

        RegulationVersion version;
        if (query.versionNumber() != null) {
            version = regulation.getVersions().stream()
                    .filter(v -> v.getVersionNumber().equals(query.versionNumber()))
                    .findFirst()
                    .orElseThrow(() -> new VersionNotFoundException(query.regulationId(), query.versionNumber()));
        } else {
            version = regulation.getVersions().stream()
                    .filter(RegulationVersion::getActive)
                    .findFirst()
                    .orElseThrow(() -> new NoActiveVersionException(query.regulationId()));
        }

        try (InputStream inputStream = fileStorageService.download(version.getFileKey())) {
            return inputStream.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException("Failed to download file: " + version.getFileKey(), e);
        }
    }
}
