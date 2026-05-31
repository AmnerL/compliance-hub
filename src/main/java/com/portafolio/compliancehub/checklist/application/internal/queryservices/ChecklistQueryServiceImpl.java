package com.portafolio.compliancehub.checklist.application.internal.queryservices;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portafolio.compliancehub.checklist.domain.model.aggregates.Checklist;
import com.portafolio.compliancehub.checklist.domain.model.exceptions.ChecklistNotFoundException;
import com.portafolio.compliancehub.checklist.domain.model.queries.GetChecklistQuery;
import com.portafolio.compliancehub.checklist.domain.service.ChecklistQueryService;
import com.portafolio.compliancehub.checklist.infrastructure.persistence.jpa.repositories.ChecklistRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChecklistQueryServiceImpl implements ChecklistQueryService {

    private final ChecklistRepository checklistRepository;

    @Override
    public Checklist handle(GetChecklistQuery query) {
        return checklistRepository.findById(query.checklistId())
                .orElseThrow(() -> new ChecklistNotFoundException(query.checklistId()));
    }
}

