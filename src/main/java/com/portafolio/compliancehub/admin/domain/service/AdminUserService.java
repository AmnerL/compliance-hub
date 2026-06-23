package com.portafolio.compliancehub.admin.domain.service;

import java.util.List;
import com.portafolio.compliancehub.auth.domain.model.aggregates.User;
import com.portafolio.compliancehub.auth.domain.model.valueobjects.RoleType;

public interface AdminUserService {
    List<User> listUsers();
    User changeUserRole(Long userId, RoleType newRole);
    User changeUserStatus(Long userId, boolean active);
}
