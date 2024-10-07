package com.scribe.backend.backend.service;


import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.scribe.backend.backend.entity.Role;
import com.scribe.backend.backend.entity.User;
import com.scribe.backend.backend.repository.RoleRepository;
import com.scribe.backend.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(String username, String password, String roleName) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        Role role = roleRepository.findByName(roleName)
                      .orElseThrow(() -> new RuntimeException("Role not found"));
        
        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(role)
                .is_author(false) // Default value
                .reading_streak(0) // Default value
                .build();

        userRepository.save(user);
    }
}
