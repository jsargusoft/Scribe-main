package com.scribe.backend.backend.dto;

import jakarta.validation.constraints.*;

import lombok.Data;

@Data
public class UserRegister {

    @NotBlank(message = "First Name is required.")
    private String firstName;

    @NotBlank(message = "Name is required.")
    private String lastName;

    @NotBlank(message = "Name is required.")
    private String username;

    @NotEmpty(message = "Email is required.")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required.")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*()]).{6,}$", message = "Password must be atleast 6 characters long and combination of uppercase letters, lowercase letters, numbers, special characters.")
    private String password;

    @NotNull
    @Pattern(regexp = "^[0-9]*$", message = "Phone number must contain only digits")
    @Size(min = 10, max = 10, message = "Phone number must contain exactly 10 digits")
    private String phone;
    
    private boolean isAuthor;

    @NotBlank(message = "Role is required.")
    private String role; 
}
