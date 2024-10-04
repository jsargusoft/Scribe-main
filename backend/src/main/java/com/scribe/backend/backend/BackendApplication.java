package com.scribe.backend.backend;

import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.scribe.backend.backend.entity.Role;
import com.scribe.backend.backend.entity.User;
import com.scribe.backend.backend.enums.Roles;
import com.scribe.backend.backend.security.repository.RoleRepository;
import com.scribe.backend.backend.security.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@SpringBootApplication
@RequiredArgsConstructor
public class BackendApplication implements ApplicationRunner{

	private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

 @Override
    public void run(ApplicationArguments args) throws Exception {
        createUsers();
    }

    public void createUsers() {
        if(!userRepository.findAll().isEmpty())
            return;

        Role roleAdmin = roleRepository.findByName(Roles.ADMIN.name()).get();
        Role roleUser = roleRepository.findByName(Roles.USER.name()).get();

        User admin = User.builder()
                .user_id(0)
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .role(roleAdmin)
                .build();

        User user = User.builder()
                .user_id(0)
                .username("user")
                .password(passwordEncoder.encode("user"))
                .role(roleUser)
                .build();


        userRepository.saveAll(List.of(user, admin));
    }
}

