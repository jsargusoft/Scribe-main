package com.scribe.backend.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scribe.backend.backend.DTO.UserRegister;
import com.scribe.backend.backend.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/register")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> register(@RequestBody UserRegister userRegister) {
        userService.registerUser(userRegister.getUsername(), userRegister.getPassword(), userRegister.getRole());
        return ResponseEntity.ok("User registered successfully");
    }
}
