package com.portafolio.compliancehub.regulation.interfaces.rest.transform;

import java.util.List;
import java.util.stream.Collectors;

import com.portafolio.compliancehub.regulation.domain.model.aggregates.Regulation;
import com.portafolio.compliancehub.regulation.domain.model.entities.RegulationVersion;
import com.portafolio.compliancehub.regulation.interfaces.rest.resource.RegulationResponseResource;
import com.portafolio.compliancehub.regulation.interfaces.rest.resource.VersionResponseResource;

public class RegulationResponseFromAggregateAssembler {
    public static RegulationResponseResource fromAggregate(Regulation regulation) {
        List<VersionResponseResource> versionResources = regulation.getVersions().stream()
                .map(RegulationResponseFromAggregateAssembler::toVersionResource)
                .collect(Collectors.toList());

        return new RegulationResponseResource(
                regulation.getId(),
                regulation.getTitle(),
                regulation.getPublicationDate(),
                regulation.getIssuingEntity(),
                regulation.getCategory(),
                regulation.getStatus(),
                versionResources);
    }

    private static VersionResponseResource toVersionResource(RegulationVersion version) {
        return new VersionResponseResource(
                version.getId(),
                version.getVersionNumber(),
                version.getFileName(),
                version.getFileSize(),
                version.getUploadDate(),
                version.getActive());
    }
}
