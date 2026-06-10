package com.portafolio.compliancehub.ai.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

public record ConsultRequestResource(@NotBlank String question) {

}
