package com.portafolio.compliancehub.checklist.interfaces.rest;

import com.portafolio.compliancehub.auth.infrastructure.authorization.sfs.model.UserDetailsImpl;
import com.portafolio.compliancehub.checklist.domain.model.aggregates.Checklist;
import com.portafolio.compliancehub.checklist.domain.model.commands.AddItemCommand;
import com.portafolio.compliancehub.checklist.domain.model.commands.CreateChecklistCommand;
import com.portafolio.compliancehub.checklist.domain.model.commands.RemoveItemCommand;
import com.portafolio.compliancehub.checklist.domain.model.commands.UpdateItemDescriptionCommand;
import com.portafolio.compliancehub.checklist.domain.model.commands.UpdateItemStatusCommand;
import com.portafolio.compliancehub.checklist.domain.model.queries.GetChecklistQuery;
import com.portafolio.compliancehub.checklist.domain.service.ChecklistCommandService;
import com.portafolio.compliancehub.checklist.domain.service.ChecklistQueryService;
import com.portafolio.compliancehub.checklist.interfaces.rest.resources.*;
import com.portafolio.compliancehub.checklist.interfaces.rest.transform.ChecklistResponseFromAggregateAssembler;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/checklists")
public class ChecklistController {

    private final ChecklistCommandService commandService;
    private final ChecklistQueryService queryService;

    @PostMapping
    public ResponseEntity<ChecklistResponseResource> create(
            @Valid @RequestBody CreateChecklistResource resource) {
        var command = new CreateChecklistCommand(resource.regulationId(), resource.items());
        var checklist = commandService.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ChecklistResponseFromAggregateAssembler.fromAggregate(checklist));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChecklistResponseResource> get(@PathVariable Long id) {
        var query = new GetChecklistQuery(id);
        var checklist = queryService.handle(query);
        return ResponseEntity.ok(ChecklistResponseFromAggregateAssembler.fromAggregate(checklist));
    }

    @PatchMapping("/{checklistId}/items/{itemId}")
    public ResponseEntity<ChecklistResponseResource> updateItem(
            @PathVariable Long checklistId,
            @PathVariable Long itemId,
            @Valid @RequestBody UpdateItemStatusResource resource,
            @AuthenticationPrincipal UserDetails userDetails) {

        Long userId = extractUserId(userDetails);
        var command = new UpdateItemStatusCommand(checklistId, itemId, resource.newStatus(), userId);
        var checklist = commandService.handle(command);
        return ResponseEntity.ok(ChecklistResponseFromAggregateAssembler.fromAggregate(checklist));
    }

    private Long extractUserId(UserDetails userDetails) {

        if (userDetails instanceof UserDetailsImpl custom) {
            return custom.getUserId();
        }

        return Long.valueOf(userDetails.getUsername());
    }

    @PostMapping("/{checklistId}/items")
    public ResponseEntity<ChecklistResponseResource> addItem(
            @PathVariable Long checklistId,
            @Valid @RequestBody AddItemResource resource) {
        AddItemCommand command = new AddItemCommand(checklistId, resource.description());
        Checklist checklist = commandService.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ChecklistResponseFromAggregateAssembler.fromAggregate(checklist));
    }

    @DeleteMapping("/{checklistId}/items/{itemId}")
    public ResponseEntity<ChecklistResponseResource> removeItem(
            @PathVariable Long checklistId,
            @PathVariable Long itemId) {
        RemoveItemCommand command = new RemoveItemCommand(checklistId, itemId);
        Checklist checklist = commandService.handle(command);
        return ResponseEntity.ok(ChecklistResponseFromAggregateAssembler.fromAggregate(checklist));
    }

    @PatchMapping("/{checklistId}/items/{itemId}/description")
    public ResponseEntity<ChecklistResponseResource> updateItemDescription(
            @PathVariable Long checklistId,
            @PathVariable Long itemId,
            @Valid @RequestBody UpdateItemDescriptionResource resource) {
        UpdateItemDescriptionCommand command = new UpdateItemDescriptionCommand(checklistId, itemId,
                resource.newDescription());
        Checklist checklist = commandService.handle(command);
        return ResponseEntity.ok(ChecklistResponseFromAggregateAssembler.fromAggregate(checklist));
    }
}