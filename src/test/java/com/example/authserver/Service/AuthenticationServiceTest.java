package com.example.authserver.Service;

import com.example.authserver.Entities.ApplicationUser;
import com.example.authserver.Entities.Role;
import com.example.authserver.Repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {
    @InjectMocks
    AuthenticationService authenticationService;

    @Mock
    UserRepository userRepository;

    @Test
    void registerUser() {
        /*Set<Role> roles =new HashSet<>();
        roles.add(new Role(1,"Admin"));
        var user = new ApplicationUser("1","egemen","password",roles);

        when(userRepository.save(any(ApplicationUser.class))).thenReturn(user);

        var actual = authenticationService.registerUser("egemen","password");

        assertEquals(user,actual);*/
    }

    @Test
    void loginUser() {
    }
}