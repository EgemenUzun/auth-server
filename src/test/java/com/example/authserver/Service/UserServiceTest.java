package com.example.authserver.Service;

import com.example.authserver.Entities.ApplicationUser;
import com.example.authserver.Entities.Role;
import com.example.authserver.Models.RegistrationDTO;
import com.example.authserver.Repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void loadUserByUsername_Success_Test(){
        //Arrange
        Set<Role> roles = new HashSet<>();
        Role role = new Role(1,"Admin");
        roles.add(role);
        String userName ="testUser";
        String password = "testPassword";
        var user =new ApplicationUser(UUID.randomUUID().toString(),
                userName,password,roles);
        when(userRepository.findByUsername(userName)).thenReturn(Optional.of(user));

        //Act
        var response = userService.loadUserByUsername(userName);

        //Assert
        assertEquals(user,response);
    }
    @Test
    public void loadUserByUsername_Failed_Test(){
    //Arrange
        String userName ="testUser";
        when(userRepository.findByUsername(userName))
                .thenThrow(new UsernameNotFoundException("user is not valid"));

        //Then
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(userName));
    }

    @Test
    public void changePassword_Test(){
        //Arrange
        Set<Role> roles = new HashSet<>();
        Role role = new Role(1,"Admin");
        roles.add(role);
        String userName ="testUser";
        String password = "testPassword";
        var body = new RegistrationDTO(userName,password);
        var user =new ApplicationUser(UUID.randomUUID().toString(),
                userName,password,roles);
        when(userRepository.findByUsername(userName)).thenReturn(Optional.of(user));
        when(encoder.encode(password)).thenReturn("Encodede_Password");
        when(userRepository.save(any())).thenReturn(user);

        //Act
        var response = userService.changePassword(body);

        //Assert
        assertEquals(user,response);
    }
}