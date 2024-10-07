package com.scribe.backend.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
// import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class User implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer user_id;

    @Column(nullable = false, length = 255)
    private String username;

    // @Column(nullable = false, unique = true, length = 255)
    // private String email;

    @Column(nullable = false, unique = true, length = 255)
    private String password;

    // @Column(nullable = false)
    // private LocalDate created_at;

    @Column
    @Builder.Default
    private boolean is_author=false;

    @ManyToOne()
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user")
    private Set<Token> tokens;

    @Column(nullable = false)
    @Builder.Default
    private Integer reading_streak = 0;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return Collections.singletonList(new SimpleGrantedAuthority(role.getAuthority()));
   
}

}