package com.portafolio.compliancehub.ai.domain.model.aggregates;

import java.util.List;

import com.portafolio.compliancehub.ai.domain.model.valueobjects.Source;
import com.portafolio.compliancehub.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "consultations")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Consultation extends AuditableAbstractAggregateRoot<Consultation> {
    private Long userId;
    private String question;
    private String answer;

    @ElementCollection
    private List<Source> sources;

    private Boolean feedback;

    public Consultation(Long userId, String question, String answer, List<Source> sources) {
        this.userId = userId;
        this.question = question;
        this.answer = answer;
        this.sources = sources;
    }

    public void registerFeedback(boolean useful) {
        this.feedback = useful;
    }
}
