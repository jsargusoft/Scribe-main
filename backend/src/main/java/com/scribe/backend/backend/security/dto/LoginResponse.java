package com.scribe.backend.backend.security.dto;

// import com.scribe.backend.backend.entity.Token;

public record LoginResponse(        
    
boolean isLogged,
String role,
String name

) {}
