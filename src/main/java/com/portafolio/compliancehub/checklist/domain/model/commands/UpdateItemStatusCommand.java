package com.portafolio.compliancehub.checklist.domain.model.commands;

import com.portafolio.compliancehub.checklist.domain.model.valueobjects.ItemStatus;

public record UpdateItemStatusCommand(Long checklistId, Long itemId, ItemStatus newStatus, Long userId) {

}
