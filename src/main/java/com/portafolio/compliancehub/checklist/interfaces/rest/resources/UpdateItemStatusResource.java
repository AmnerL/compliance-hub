package com.portafolio.compliancehub.checklist.interfaces.rest.resources;

import com.portafolio.compliancehub.checklist.domain.model.valueobjects.ItemStatus;

import jakarta.validation.constraints.NotNull;

public record UpdateItemStatusResource(
        @NotNull ItemStatus newStatus) {
}