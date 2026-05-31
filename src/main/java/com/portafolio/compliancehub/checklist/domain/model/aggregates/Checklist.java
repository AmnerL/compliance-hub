package com.portafolio.compliancehub.checklist.domain.model.aggregates;

import com.portafolio.compliancehub.checklist.domain.model.entities.ChecklistItem;
import com.portafolio.compliancehub.checklist.domain.model.exceptions.ItemNotFoundInChecklistException;
import com.portafolio.compliancehub.checklist.domain.model.valueobjects.ItemStatus;
import com.portafolio.compliancehub.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "checklists")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Checklist extends AuditableAbstractAggregateRoot<Checklist> {

    private Long regulationId;

    @OneToMany(mappedBy = "checklist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChecklistItem> items = new ArrayList<>();

    public Checklist(Long regulationId, List<String> descriptions) {
        this.regulationId = regulationId;
        for (String desc : descriptions) {
            ChecklistItem item = new ChecklistItem(desc);
            item.setChecklist(this);
            this.items.add(item);
        }
    }

    public void updateItemStatus(Long itemId, ItemStatus newStatus, Long userId) {
        ChecklistItem item = items.stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new ItemNotFoundInChecklistException(getId(), itemId));
        item.updateStatus(newStatus, userId);
    }

    public double getProgressPercentage() {
        long completed = items.stream().filter(ChecklistItem::isCompleted).count();
        long total = items.size();
        return total == 0 ? 0 : (completed * 100.0) / total;
    }

    public void addItem(String description) {
        ChecklistItem item = new ChecklistItem(description);
        item.setChecklist(this);
        this.items.add(item);
    }

    public void removeItem(Long itemId) {
        ChecklistItem item = items.stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Item not found in checklist"));
        items.remove(item);
    }

    public void updateItemDescription(Long itemId, String newDescription) {
        ChecklistItem item = items.stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Item not found in checklist"));
        item.setDescription(newDescription);
    }
}
