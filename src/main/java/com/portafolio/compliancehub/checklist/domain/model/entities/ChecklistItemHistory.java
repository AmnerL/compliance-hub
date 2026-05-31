package com.portafolio.compliancehub.checklist.domain.model.entities;

import com.portafolio.compliancehub.checklist.domain.model.valueobjects.ItemStatus;
import com.portafolio.compliancehub.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "checklist_item_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ChecklistItemHistory extends AuditableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ItemStatus previousStatus;

    @Enumerated(EnumType.STRING)
    private ItemStatus newStatus;

    private Long changedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    @Setter
    private ChecklistItem item;

    public ChecklistItemHistory(ItemStatus previous, ItemStatus newStatus, Long userId) {
        this.previousStatus = previous;
        this.newStatus = newStatus;
        this.changedBy = userId;
    }
}
