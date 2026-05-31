package com.portafolio.compliancehub.checklist.interfaces.rest.resources;

import java.util.List;

public record ChecklistResponseResource(
        Long id,
        Long regulationId,
        double progress,
        List<ChecklistItemResponseResource> items) {
}
