package com.scribe.backend.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"resource", "operation"})})
public class Permission implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String resource;
    @Column(nullable = false)
    private String operation;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles;
    
    @Override
    public String getAuthority() {
        return String.format("%s:%s", resource.toUpperCase(), operation.toUpperCase());
    }
}