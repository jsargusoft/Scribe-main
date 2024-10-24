package com.scribe.backend.backend.service.impl;


import org.springframework.stereotype.Service;

import java.time.LocalDate;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.scribe.backend.backend.dto.UserRegister;
import com.scribe.backend.backend.entity.Role;
import com.scribe.backend.backend.entity.User;
import com.scribe.backend.backend.repository.RoleRepository;
import com.scribe.backend.backend.repository.UserRepository;
import com.scribe.backend.backend.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(UserRegister userRegister) {
        if (userRepository.findByEmail(userRegister.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        Role role = roleRepository.findByName(userRegister.getRole())
                      .orElseThrow(() -> new RuntimeException("Role not found"));

        boolean author;

        if (userRegister.getRole().equals("AUTHOR")) {
            author = true;
        }
        else{
            author = false;
        }
        
        User user = User.builder()
                .firstName(userRegister.getFirstName())
                .lastName(userRegister.getLastName())
                .username(userRegister.getUsername())
                .email(userRegister.getEmail())
                .password(passwordEncoder.encode(userRegister.getPassword()))
                .created_at(LocalDate.now())
                .phone(userRegister.getPhone())
                .role(role)
                .is_author(author) 
                .build();

        userRepository.save(user);
    }

    public User getCurrentlyLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("No user is currently logged in.");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        User user = userRepository.findByEmail(userDetails.getUsername()).orElse(null);

        return user;
    }
}
