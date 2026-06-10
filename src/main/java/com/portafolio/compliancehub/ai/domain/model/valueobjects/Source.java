package com.portafolio.compliancehub.ai.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Embeddable
@Getter
@AllArgsConstructor
public class Source {
    private String documentTitle;
    private Integer pageNumber;
    private String excerpt;
}
