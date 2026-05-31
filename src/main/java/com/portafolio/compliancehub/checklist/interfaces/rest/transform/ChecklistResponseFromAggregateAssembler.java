package com.portafolio.compliancehub.checklist.interfaces.rest.transform;

import com.portafolio.compliancehub.checklist.domain.model.aggregates.Checklist;
import com.portafolio.compliancehub.checklist.interfaces.rest.resources.ChecklistItemResponseResource;
import com.portafolio.compliancehub.checklist.interfaces.rest.resources.ChecklistResponseResource;

import java.util.stream.Collectors;

public class ChecklistResponseFromAggregateAssembler {
    public static ChecklistResponseResource fromAggregate(Checklist checklist) {
        var items = checklist.getItems().stream()
                .map(item -> new ChecklistItemResponseResource(
                        item.getId(),
                        item.getDescription(),
                        item.getStatus()))
                .collect(Collectors.toList());
        return new ChecklistResponseResource(
                checklist.getId(),
                checklist.getRegulationId(),
                checklist.getProgressPercentage(),
                items);
    }
}
