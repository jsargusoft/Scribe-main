package com.scribe.backend.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scribe.backend.backend.dto.UserRegister;
import com.scribe.backend.backend.entity.Stories;
import com.scribe.backend.backend.entity.User;
import com.scribe.backend.backend.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegister userRegister) {

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "User registered successfully");
        userService.registerUser(userRegister);
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUserDetails() {
        User user = userService.getCurrentlyLoggedInUser();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/story")
    public List<Stories> getStoryById(@RequestParam Integer userId){
        return userService.getStoryById(userId);
    }
}
