package com.example.authserver;

import com.example.authserver.Entities.ApplicationUser;
import com.example.authserver.Entities.Role;
import com.example.authserver.Repositories.RoleRepository;
import com.example.authserver.Repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@SpringBootApplication
public class AuthServerApplication {

    public static void main(String[] args) throws FileNotFoundException {
        PrintStream originalOut = System.out;
        PrintStream fileOut = new PrintStream("./out.txt");
        System.setOut(fileOut);
        SpringApplication.run(AuthServerApplication.class, args);
    }
}
