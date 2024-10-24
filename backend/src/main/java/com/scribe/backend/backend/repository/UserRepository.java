package com.scribe.backend.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scribe.backend.backend.entity.User;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer>{

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

}
