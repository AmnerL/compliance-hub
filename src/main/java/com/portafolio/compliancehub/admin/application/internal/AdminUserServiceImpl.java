package com.portafolio.compliancehub.admin.application.internal;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portafolio.compliancehub.admin.domain.model.exceptions.UserNotFoundException;
import com.portafolio.compliancehub.admin.domain.service.AdminUserService;
import com.portafolio.compliancehub.auth.domain.model.aggregates.User;
import com.portafolio.compliancehub.auth.domain.model.valueobjects.RoleType;
import com.portafolio.compliancehub.auth.infrastructure.persistence.jpa.repositories.UserRepository;

@Service
public class AdminUserServiceImpl implements AdminUserService {

    private final UserRepository userRepository;

    public AdminUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User changeUserRole(Long userId, RoleType newRole) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        user.assignRole(newRole);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User changeUserStatus(Long userId, boolean active) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        if (active) {
            user.activate();
        } else {
            user.deactivate();
        }
        return userRepository.save(user);
    }
}
