package com.portafolio.compliancehub.regulation.interfaces.rest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.portafolio.compliancehub.regulation.domain.model.aggregates.Regulation;
import com.portafolio.compliancehub.regulation.domain.model.commands.AddVersionCommand;
import com.portafolio.compliancehub.regulation.domain.model.commands.UploadRegulationCommand;
import com.portafolio.compliancehub.regulation.domain.model.entities.RegulationVersion;
import com.portafolio.compliancehub.regulation.domain.model.queries.DownloadRegulationQuery;
import com.portafolio.compliancehub.regulation.domain.model.queries.GetRegulationQuery;
import com.portafolio.compliancehub.regulation.domain.model.queries.ListRegulationsQuery;
import com.portafolio.compliancehub.regulation.domain.model.valueobjects.Category;
import com.portafolio.compliancehub.regulation.domain.service.RegulationCommandService;
import com.portafolio.compliancehub.regulation.domain.service.RegulationQueryService;
import com.portafolio.compliancehub.regulation.interfaces.rest.resource.RegulationResponseResource;
import com.portafolio.compliancehub.regulation.interfaces.rest.resource.UploadRegulationResource;
import com.portafolio.compliancehub.regulation.interfaces.rest.transform.RegulationResponseFromAggregateAssembler;
import com.portafolio.compliancehub.regulation.interfaces.rest.transform.UploadRegulationCommandFromResourceAssembler;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/regulations")
@AllArgsConstructor
public class RegulationController {
    private final RegulationCommandService regulationCommandService;
    private final RegulationQueryService regulationQueryService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RegulationResponseResource> uploadRegulation(
            @Valid @RequestPart("metadata") UploadRegulationResource resource,
            @RequestPart("file") MultipartFile file) {

        UploadRegulationCommand command = UploadRegulationCommandFromResourceAssembler
                .toCommandFromResource(resource, file);
        Regulation regulation = regulationCommandService.handle(command);
        RegulationResponseResource response = RegulationResponseFromAggregateAssembler.fromAggregate(regulation);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegulationResponseResource> getRegulation(@PathVariable Long id) {
        GetRegulationQuery query = new GetRegulationQuery(id);
        Regulation regulation = regulationQueryService.handle(query);
        RegulationResponseResource response = RegulationResponseFromAggregateAssembler.fromAggregate(regulation);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<RegulationResponseResource>> listRegulations(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Category category,
            @PageableDefault(size = 20) Pageable pageable) {

        ListRegulationsQuery query = new ListRegulationsQuery(search, category, pageable);
        Page<Regulation> regulationPage = regulationQueryService.handle(query);
        Page<RegulationResponseResource> responsePage = regulationPage
                .map(RegulationResponseFromAggregateAssembler::fromAggregate);
        return ResponseEntity.ok(responsePage);
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> downloadRegulation(
            @PathVariable Long id,
            @RequestParam(required = false) Integer versionNumber) {

        DownloadRegulationQuery query = new DownloadRegulationQuery(id, versionNumber);
        byte[] fileContent = regulationQueryService.handle(query);

        Regulation regulation = regulationQueryService.handle(new GetRegulationQuery(id));
        String fileName = regulation.getVersions().stream()
                .filter(v -> versionNumber == null ? v.getActive() : v.getVersionNumber().equals(versionNumber))
                .findFirst()
                .map(RegulationVersion::getFileName)
                .orElse("reglamento.pdf");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentLength(fileContent.length);

        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
    }

    @PostMapping("/{id}/versions")
    public ResponseEntity<RegulationResponseResource> addVersion(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {

        AddVersionCommand command = new AddVersionCommand(id, file);
        Regulation regulation = regulationCommandService.handle(command);
        RegulationResponseResource response = RegulationResponseFromAggregateAssembler.fromAggregate(regulation);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
