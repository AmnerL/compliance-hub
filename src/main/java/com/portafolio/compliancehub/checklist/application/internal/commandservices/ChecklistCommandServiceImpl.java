package com.portafolio.compliancehub.checklist.application.internal.commandservices;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portafolio.compliancehub.checklist.domain.service.ChecklistCommandService;

import com.portafolio.compliancehub.checklist.domain.model.aggregates.Checklist;
import com.portafolio.compliancehub.checklist.domain.model.commands.AddItemCommand;
import com.portafolio.compliancehub.checklist.domain.model.commands.CreateChecklistCommand;
import com.portafolio.compliancehub.checklist.domain.model.commands.RemoveItemCommand;
import com.portafolio.compliancehub.checklist.domain.model.commands.UpdateItemDescriptionCommand;
import com.portafolio.compliancehub.checklist.domain.model.commands.UpdateItemStatusCommand;
import com.portafolio.compliancehub.checklist.domain.model.exceptions.ChecklistNotFoundException;
import com.portafolio.compliancehub.checklist.infrastructure.persistence.jpa.repositories.ChecklistRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ChecklistCommandServiceImpl implements ChecklistCommandService {

    private final ChecklistRepository checklistRepository;

    @Override
    public Checklist handle(CreateChecklistCommand command) {
        Checklist checklist = new Checklist(command.regulationId(), command.items());
        return checklistRepository.save(checklist);
    }

    @Override
    public Checklist handle(UpdateItemStatusCommand command) {
        Checklist checklist = checklistRepository.findById(command.checklistId())
                .orElseThrow(() -> new ChecklistNotFoundException(command.checklistId()));
        checklist.updateItemStatus(command.itemId(), command.newStatus(), command.userId());
        return checklistRepository.save(checklist);
    }

    @Override
    public Checklist handle(AddItemCommand command) {
        Checklist checklist = checklistRepository.findById(command.checklistId())
                .orElseThrow(() -> new ChecklistNotFoundException(command.checklistId()));
        checklist.addItem(command.description());
        return checklistRepository.save(checklist);
    }

    @Override
    public Checklist handle(RemoveItemCommand command) {
        Checklist checklist = checklistRepository.findById(command.checklistId())
                .orElseThrow(() -> new ChecklistNotFoundException(command.checklistId()));
        checklist.removeItem(command.itemId());
        return checklistRepository.save(checklist);
    }

    @Override
    public Checklist handle(UpdateItemDescriptionCommand command) {
        Checklist checklist = checklistRepository.findById(command.checklistId())
                .orElseThrow(() -> new ChecklistNotFoundException(command.checklistId()));
        checklist.updateItemDescription(command.itemId(), command.newDescription());
        return checklistRepository.save(checklist);
    }
}
