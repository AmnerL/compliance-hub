package com.portafolio.compliancehub.regulation.domain.service;

import org.springframework.data.domain.Page;

import com.portafolio.compliancehub.regulation.domain.model.aggregates.Regulation;
import com.portafolio.compliancehub.regulation.domain.model.queries.DownloadRegulationQuery;
import com.portafolio.compliancehub.regulation.domain.model.queries.GetRegulationQuery;
import com.portafolio.compliancehub.regulation.domain.model.queries.ListRegulationsQuery;

public interface RegulationQueryService {
    Regulation handle(GetRegulationQuery query);

    Page<Regulation> handle(ListRegulationsQuery query);

    byte[] handle(DownloadRegulationQuery query);
}
