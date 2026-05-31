package com.portafolio.compliancehub.checklist.domain.model.entities;

import com.portafolio.compliancehub.checklist.domain.model.aggregates.Checklist;
import com.portafolio.compliancehub.checklist.domain.model.valueobjects.ItemStatus;
import com.portafolio.compliancehub.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "checklist_items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ChecklistItem extends AuditableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String description;

    @Enumerated(EnumType.STRING)
    private ItemStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checklist_id")
    @Setter
    private Checklist checklist;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChecklistItemHistory> history = new ArrayList<>();

    public ChecklistItem(String description) {
        this.description = description;
        this.status = ItemStatus.PENDING;
    }

    public void updateStatus(ItemStatus newStatus, Long userId) {
        ItemStatus previousStatus = this.status;
        this.status = newStatus;
        ChecklistItemHistory entry = new ChecklistItemHistory(previousStatus, newStatus, userId);
        entry.setItem(this);
        this.history.add(entry);
    }

    public boolean isCompleted() {
        return this.status == ItemStatus.COMPLIED;
    }
}
