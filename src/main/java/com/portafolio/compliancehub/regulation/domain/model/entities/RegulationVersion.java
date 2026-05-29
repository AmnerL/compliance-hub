package com.portafolio.compliancehub.regulation.domain.model.entities;

import java.time.LocalDateTime;

import com.portafolio.compliancehub.regulation.domain.model.aggregates.Regulation;
import com.portafolio.compliancehub.shared.domain.model.entities.AuditableModel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "regulation_versions")
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegulationVersion extends AuditableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer versionNumber;

    private String fileKey;

    private String fileName;

    private Long fileSize;

    private LocalDateTime uploadDate;

    private Boolean active;

    @Setter
    @ManyToOne
    @JoinColumn(name = "regulation_id")
    private Regulation regulation;

    public RegulationVersion(Integer versionNumber, String fileKey, String fileName, Long fileSize,
            LocalDateTime uploadDate, Boolean active) {
        this.versionNumber = versionNumber;
        this.fileKey = fileKey;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.uploadDate = uploadDate;
        this.active = true;
    }

    public void setInactive() {
        this.active = false;
    }

}
