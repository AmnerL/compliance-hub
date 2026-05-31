package com.portafolio.compliancehub.checklist.domain.model.exceptions;

public class ItemNotFoundInChecklistException extends RuntimeException {
    public ItemNotFoundInChecklistException(Long checklistId, Long itemId) {
        super("Item with ID " + itemId + " not found in checklist with ID " + checklistId + ".");
    }
}
