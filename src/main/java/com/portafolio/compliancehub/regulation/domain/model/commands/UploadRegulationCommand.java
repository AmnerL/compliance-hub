package com.portafolio.compliancehub.regulation.domain.model.commands;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import com.portafolio.compliancehub.regulation.domain.model.valueobjects.Category;
import com.portafolio.compliancehub.regulation.domain.model.valueobjects.IssuingEntity;

public record UploadRegulationCommand(
        String title,
        LocalDate publicationDate,
        IssuingEntity issuingEntity,
        Category category,
        MultipartFile file) {

}
