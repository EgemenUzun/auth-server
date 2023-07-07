package com.example.authserver.Configuration;

import com.example.authserver.Entities.Role;
import com.example.authserver.Repositories.RoleRepository;
import com.example.authserver.Repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class RoleConfiguration {
    @Bean
    CommandLineRunner run(RoleRepository roleRepository , UserRepository userRepository, PasswordEncoder passwordEncoder){
        return args -> {
            if(roleRepository.findByAuthority("Admin").isPresent())return;
            Role adminRole = roleRepository.save(new Role("Admin"));
            roleRepository.save(new Role("User"));
        };
    }
}
