package com.portafolio.compliancehub.checklist.interfaces.rest.resources;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CreateChecklistResource(
        @NotNull Long regulationId,
        @NotEmpty List<String> items) {
}
