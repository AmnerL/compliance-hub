package com.portafolio.compliancehub.auth.domain.services;

import com.portafolio.compliancehub.auth.domain.model.aggregates.User;
import com.portafolio.compliancehub.auth.domain.model.queries.GetUserByEmailQuery;

public interface UserQueryService {
    User getUserByEmail(GetUserByEmailQuery query);
}
