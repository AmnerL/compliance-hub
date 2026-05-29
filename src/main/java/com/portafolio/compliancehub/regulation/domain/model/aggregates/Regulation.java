package com.portafolio.compliancehub.regulation.domain.model.aggregates;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.portafolio.compliancehub.regulation.domain.model.entities.RegulationVersion;
import com.portafolio.compliancehub.regulation.domain.model.valueobjects.Category;
import com.portafolio.compliancehub.regulation.domain.model.valueobjects.IssuingEntity;
import com.portafolio.compliancehub.regulation.domain.model.valueobjects.RegulationStatus;
import com.portafolio.compliancehub.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import jakarta.persistence.Entity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "regulations")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Regulation extends AuditableAbstractAggregateRoot<Regulation> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private LocalDate publicationDate;

    @Enumerated(EnumType.STRING)
    private IssuingEntity issuingEntity;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private RegulationStatus status;

    @OneToMany(mappedBy = "regulation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RegulationVersion> versions;

    public Regulation(String title, LocalDate publicationDate, IssuingEntity issuingEntity, Category category) {
        this.title = title;
        this.publicationDate = publicationDate;
        this.issuingEntity = issuingEntity;
        this.category = category;
        this.status = RegulationStatus.ACTIVE;
        this.versions = new ArrayList<>();
    }

    public void addVersion(RegulationVersion version) {
        this.versions.forEach(v -> v.setInactive());
        version.setRegulation(this);
        this.versions.add(version);
    }
}
