package com.scribe.backend.backend.entity;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role implements GrantedAuthority{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer role_id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @OneToMany(mappedBy = "role",fetch = FetchType.EAGER)
    private Set<User> users;    

    // @ManyToMany(fetch = FetchType.EAGER)
    // @JoinTable(name = "role_permission", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
    // private Set<Permission> permissions;

    // public Set<Permission> getPermissions() {
    //     return permissions;
    // }

    // public void setPermissions(Set<Permission> permissions) {
    //     this.permissions = permissions;
    // }

    @Override
    public String getAuthority() {
        String role = this.name.toUpperCase();
        return String.format("ROLE_%s", role);
    }

}

