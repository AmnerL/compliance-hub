package com.portafolio.compliancehub.ai.interfaces.rest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portafolio.compliancehub.ai.domain.model.commands.ConsultCommand;
import com.portafolio.compliancehub.ai.domain.model.queries.GetConsultationHistoryQuery;
import com.portafolio.compliancehub.ai.domain.service.AICommandService;
import com.portafolio.compliancehub.ai.domain.service.AIQueryService;
import com.portafolio.compliancehub.ai.interfaces.rest.resources.ConsultRequestResource;
import com.portafolio.compliancehub.ai.interfaces.rest.resources.ConsultationResponseResource;
import com.portafolio.compliancehub.ai.interfaces.rest.resources.FeedbackResource;
import com.portafolio.compliancehub.ai.interfaces.rest.transform.ConsultCommandFromResourceAssembler;
import com.portafolio.compliancehub.ai.interfaces.rest.transform.ConsultationResponseFromAggregateAssembler;
import com.portafolio.compliancehub.auth.infrastructure.authorization.sfs.model.UserDetailsImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AIController {
    private final AICommandService aiCommandService;
    private final AIQueryService aiQueryService;

    @PostMapping("/consult")
    public ResponseEntity<ConsultationResponseResource> consult(
            @Valid @RequestBody ConsultRequestResource resource,
            @AuthenticationPrincipal UserDetailsImpl user) {
        ConsultCommand command = ConsultCommandFromResourceAssembler.toCommandFromResource(user.getUserId(), resource);
        var consultation = aiCommandService.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ConsultationResponseFromAggregateAssembler.fromAggregate(consultation));
    }

    @GetMapping("/consultations")
    public ResponseEntity<Page<ConsultationResponseResource>> getHistory(
            @AuthenticationPrincipal UserDetailsImpl user,
            @PageableDefault(size = 20) Pageable pageable) {
        var query = new GetConsultationHistoryQuery(user.getUserId(), pageable);
        var page = aiQueryService.handle(query);
        return ResponseEntity.ok(page.map(ConsultationResponseFromAggregateAssembler::fromAggregate));
    }

    @PatchMapping("/consultations/{id}/feedback")
    public ResponseEntity<Void> giveFeedback(@PathVariable Long id,
            @Valid @RequestBody FeedbackResource resource) {
        aiCommandService.updateFeedback(id, resource.useful());
        return ResponseEntity.noContent().build();
    }

}
