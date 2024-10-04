package com.scribe.backend.backend.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scribe.backend.backend.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{

    Optional<Role> findByName(String role_name);

}
