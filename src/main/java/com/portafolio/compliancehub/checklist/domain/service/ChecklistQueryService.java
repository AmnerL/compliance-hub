package com.portafolio.compliancehub.checklist.domain.service;

import com.portafolio.compliancehub.checklist.domain.model.aggregates.Checklist;
import com.portafolio.compliancehub.checklist.domain.model.queries.GetChecklistQuery;

public interface ChecklistQueryService {
    Checklist handle(GetChecklistQuery query);
}
