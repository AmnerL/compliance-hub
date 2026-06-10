package com.portafolio.compliancehub.ai.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;

public record FeedbackResource(@NotNull Boolean useful) {

}
