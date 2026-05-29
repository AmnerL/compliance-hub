package com.portafolio.compliancehub.regulation.domain.service;

import com.portafolio.compliancehub.regulation.domain.model.aggregates.Regulation;
import com.portafolio.compliancehub.regulation.domain.model.commands.AddVersionCommand;
import com.portafolio.compliancehub.regulation.domain.model.commands.UploadRegulationCommand;

public interface RegulationCommandService {
    Regulation handle(UploadRegulationCommand command);

    Regulation handle(AddVersionCommand command);
}
