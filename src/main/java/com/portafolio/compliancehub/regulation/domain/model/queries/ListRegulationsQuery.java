package com.portafolio.compliancehub.regulation.domain.model.queries;

import org.springframework.data.domain.Pageable;

import com.portafolio.compliancehub.regulation.domain.model.valueobjects.Category;

public record ListRegulationsQuery(String searchTerm, Category categoryFilter, Pageable pageable) {

}
