package com.portafolio.compliancehub.checklist.interfaces.rest.resources;

import com.portafolio.compliancehub.checklist.domain.model.valueobjects.ItemStatus;

public record ChecklistItemResponseResource(
        Long id,
        String description,
        ItemStatus status) {
}
