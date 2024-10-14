package com.scribe.backend.backend.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scribe.backend.backend.DTO.UserRegister;
import com.scribe.backend.backend.service.UserService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/register")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody UserRegister userRegister) {
        System.out.println(userRegister);
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("messege", "User registered successfully" );
        userService.registerUser(userRegister);
        return ResponseEntity.ok(responseBody);
    }
}
