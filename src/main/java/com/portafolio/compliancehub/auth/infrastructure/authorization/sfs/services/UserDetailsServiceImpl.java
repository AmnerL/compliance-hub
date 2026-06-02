package com.portafolio.compliancehub.auth.infrastructure.authorization.sfs.services;

import com.portafolio.compliancehub.auth.domain.model.aggregates.User;
import com.portafolio.compliancehub.auth.infrastructure.authorization.sfs.model.UserDetailsImpl;
import com.portafolio.compliancehub.auth.infrastructure.persistence.jpa.repositories.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service(value = "defaultUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Long userIdLong = Long.valueOf(userId);
        User user = userRepository.findById(userIdLong)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));
        return UserDetailsImpl.build(user);
    }
}
