package com.portafolio.compliancehub.checklist.domain.model.commands;

import java.util.List;

public record CreateChecklistCommand(Long regulationId, List<String> items) {

}
