package com.portafolio.compliancehub.admin.interfaces.rest.transform;

import com.portafolio.compliancehub.admin.interfaces.rest.resources.UserAdminResponse;
import com.portafolio.compliancehub.auth.domain.model.aggregates.User;

public class UserAdminResponseFromAggregateAssembler {
    public static UserAdminResponse fromAggregate(User user) {
        return new UserAdminResponse(
            user.getId(),
            user.getEmail(),
            user.getFirstName(),
            user.getLastName(),
            user.getRole().name(),
            user.getActive(),
            user.getCreatedAt()
        );
    }
}
