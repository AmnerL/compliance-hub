package com.portafolio.compliancehub.checklist.domain.model.commands;

public record UpdateItemDescriptionCommand(Long checklistId, Long itemId, String newDescription) {

}
