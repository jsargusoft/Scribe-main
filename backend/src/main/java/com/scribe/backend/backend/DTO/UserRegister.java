package com.scribe.backend.backend.DTO;

import lombok.Data;

@Data
public class UserRegister {
    private String username;
    private String password;
    private String role; 
}
