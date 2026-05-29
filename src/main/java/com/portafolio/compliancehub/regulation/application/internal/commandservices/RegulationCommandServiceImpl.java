package com.portafolio.compliancehub.regulation.application.internal.commandservices;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portafolio.compliancehub.regulation.application.internal.outboundservices.storage.FileStorageService;
import com.portafolio.compliancehub.regulation.domain.model.aggregates.Regulation;
import com.portafolio.compliancehub.regulation.domain.model.commands.AddVersionCommand;
import com.portafolio.compliancehub.regulation.domain.model.commands.UploadRegulationCommand;
import com.portafolio.compliancehub.regulation.domain.model.entities.RegulationVersion;
import com.portafolio.compliancehub.regulation.domain.service.RegulationCommandService;
import com.portafolio.compliancehub.regulation.infrastructure.persistence.jpa.repositories.RegulationRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegulationCommandServiceImpl implements RegulationCommandService {

    private final RegulationRepository regulationRepository;
    private final FileStorageService fileStorageService;

    @Transactional
    @Override
    public Regulation handle(UploadRegulationCommand command) {
        Regulation regulation = new Regulation(
                command.title(),
                command.publicationDate(),
                command.issuingEntity(),
                command.category());
        regulation = regulationRepository.save(regulation);

        String key = "regulations/" + regulation.getId() + "/v1.pdf";
        try {
            fileStorageService.upload(key, command.file().getInputStream(), command.file().getSize());
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e);
        }

        RegulationVersion version = new RegulationVersion(
                1,
                key,
                command.file().getOriginalFilename(),
                command.file().getSize(),
                LocalDateTime.now(),
                true);
        regulation.addVersion(version);
        regulation = regulationRepository.save(regulation);
        return regulation;
    }

    @Transactional
    @Override
    public Regulation handle(AddVersionCommand command) {
        Regulation regulation = regulationRepository.findById(command.regulationId())
                .orElseThrow(() -> new RuntimeException("Regulation not found"));

        int newVersionNumber = regulation.getVersions().stream()
                .mapToInt(RegulationVersion::getVersionNumber)
                .max()
                .orElse(0) + 1;

        String key = "regulations/" + regulation.getId() + "/v" + newVersionNumber + ".pdf";
        try {
            fileStorageService.upload(key, command.file().getInputStream(), command.file().getSize());
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e);
        }

        RegulationVersion newVersion = new RegulationVersion(
                newVersionNumber,
                key,
                command.file().getOriginalFilename(),
                command.file().getSize(),
                LocalDateTime.now(),
                true);
        regulation.addVersion(newVersion);
        regulationRepository.save(regulation);
        return regulation;
    }
}
