package com.portafolio.compliancehub.checklist.domain.service;

import com.portafolio.compliancehub.checklist.domain.model.aggregates.Checklist;
import com.portafolio.compliancehub.checklist.domain.model.commands.AddItemCommand;
import com.portafolio.compliancehub.checklist.domain.model.commands.CreateChecklistCommand;
import com.portafolio.compliancehub.checklist.domain.model.commands.RemoveItemCommand;
import com.portafolio.compliancehub.checklist.domain.model.commands.UpdateItemDescriptionCommand;
import com.portafolio.compliancehub.checklist.domain.model.commands.UpdateItemStatusCommand;

public interface ChecklistCommandService {
    Checklist handle(CreateChecklistCommand command);

    Checklist handle(UpdateItemStatusCommand command);

    Checklist handle(AddItemCommand command);

    Checklist handle(RemoveItemCommand command);

    Checklist handle(UpdateItemDescriptionCommand command);
}
