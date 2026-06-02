package com.portafolio.compliancehub.notification.application.internal.outboundservices.notification;

import com.portafolio.compliancehub.regulation.domain.model.aggregates.Regulation;

public interface RegulationNotifier {
    void notifyNewRegulation(Regulation regulation);
}
