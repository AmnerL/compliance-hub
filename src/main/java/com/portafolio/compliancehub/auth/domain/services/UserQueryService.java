package com.portafolio.compliancehub.auth.domain.services;

import com.portafolio.compliancehub.auth.domain.model.aggregates.User;
import com.portafolio.compliancehub.auth.domain.model.queries.GetUserByEmailQuery;

import java.util.Optional;

public interface UserQueryService {
    Optional<User> getUserByEmail(GetUserByEmailQuery query);
}
