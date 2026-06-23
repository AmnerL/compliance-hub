package com.portafolio.compliancehub.ai.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Source {
    private String documentTitle;
    private Integer pageNumber;
    private String excerpt;
}
