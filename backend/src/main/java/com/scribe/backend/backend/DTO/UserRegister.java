package com.scribe.backend.backend.DTO;

import lombok.Data;

@Data
public class UserRegister {

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private String phone;
    private boolean isAuthor;
    private String role; 
}
