package com.scribe.backend.backend.service;


import org.springframework.stereotype.Service;

import java.time.LocalDate;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.scribe.backend.backend.DTO.UserRegister;
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

    public void registerUser(UserRegister userRegister) {
        if (userRepository.findByUsername(userRegister.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        Role role = roleRepository.findByName(userRegister.getRole())
                      .orElseThrow(() -> new RuntimeException("Role not found"));
        
        User user = User.builder()
                .firstName(userRegister.getFirstName())
                .lastName(userRegister.getLastName())
                .username(userRegister.getUsername())
                .email(userRegister.getEmail())
                .password(passwordEncoder.encode(userRegister.getPassword()))
                .created_at(LocalDate.now())
                .phone(userRegister.getPhone())
                .role(role)
                .is_author(false) // Default value
                .build();

        userRepository.save(user);
    }
}
