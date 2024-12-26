package com.scribe.backend.backend.service.impl;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.scribe.backend.backend.dto.UserRegister;
import com.scribe.backend.backend.entity.Role;
import com.scribe.backend.backend.entity.Stories;
import com.scribe.backend.backend.entity.User;
import com.scribe.backend.backend.repository.RoleRepository;
import com.scribe.backend.backend.repository.StoryRepository;
import com.scribe.backend.backend.repository.UserRepository;
import com.scribe.backend.backend.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final StoryRepository storyRepository;

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

    public User getUserById(Integer userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User updateUser(Integer userId, UserRegister userRegister) {
        User existingUser = userRepository.findById(userId)
                                          .orElseThrow(() -> new RuntimeException("User not found"));
    
        if (userRegister.getFirstName() != null && !userRegister.getFirstName().isBlank()) {
            existingUser.setFirstName(userRegister.getFirstName());
        }
    
        if (userRegister.getLastName() != null && !userRegister.getLastName().isBlank()) {
            existingUser.setLastName(userRegister.getLastName());
        }
    
        if (userRegister.getUsername() != null && !userRegister.getUsername().isBlank()) {
            existingUser.setUsername(userRegister.getUsername());
        }
    
        if (userRegister.getEmail() != null && !userRegister.getEmail().isBlank()) {
            existingUser.setEmail(userRegister.getEmail());
        }
    
        if (userRegister.getPassword() != null && !userRegister.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(userRegister.getPassword()));
        }
    
        if (userRegister.getPhone() != null && !userRegister.getPhone().isBlank()) {
            existingUser.setPhone(userRegister.getPhone());
        }
    
        return userRepository.save(existingUser);
    }

    public void deleteUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }

    @Override
    public List<Stories> getStoryById(Integer userId) {
        return storyRepository.findByAuthorId(Integer.toString(userId));
    }
}
