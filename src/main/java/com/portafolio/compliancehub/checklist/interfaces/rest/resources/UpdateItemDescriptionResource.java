package com.portafolio.compliancehub.checklist.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

public record UpdateItemDescriptionResource(@NotBlank String newDescription) {

}
