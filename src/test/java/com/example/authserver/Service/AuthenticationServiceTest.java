package com.example.authserver.Service;

import com.example.authserver.Entities.ApplicationUser;
import com.example.authserver.Entities.Role;
import com.example.authserver.Entities.TimeLog;
import com.example.authserver.Models.LoginResponseDTO;
import com.example.authserver.Repositories.RoleRepository;
import com.example.authserver.Repositories.TimeLogRepository;
import com.example.authserver.Repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.*;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class AuthenticationServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenService tokenService;
    @InjectMocks
    private  AuthenticationService authenticationService;

    @Mock
    private TimeLogRepository timeLogRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void registerUser_Successful_For_Admin(){
        //Arrange
        Set<Role> roles = new HashSet<>();
        Role role = new Role(1,"Admin");
        roles.add(role);
        String userName ="testUser";
        String password = "testPassword";
        var user =new ApplicationUser(UUID.randomUUID().toString(),
                userName,password,roles);
        when(userRepository.findAll())
                .thenReturn(emptyList());

        when(roleRepository.findByAuthority(role.getAuthority()))
                .thenReturn(Optional.of(role));

        when(userRepository.findByUsername(userName))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode(password))
                .thenReturn("encoded_password");

        when(userRepository.save(any()))
                .thenReturn(user);
        // Act
        ApplicationUser response = authenticationService.registerUser(userName,password);

        //Assert
        assertEquals(user,response);
        assertEquals(user.getAuthorities(),response.getAuthorities());
    }
    @Test
    public void registerUser_Successful_For_User(){
        //Arrange
        Set<Role> roles = new HashSet<>();
        var role = new Role(1,"User");
        roles.add(role);
        String userName ="testUser";
        String password = "testPassword";
        var user =new ApplicationUser(UUID.randomUUID().toString(),
                userName,password,roles);
        var newuser =new ApplicationUser("1","test","password",roles);
        ArrayList<ApplicationUser> users = new ArrayList<>();
        users.add(newuser);

        when(userRepository.findAll())
                .thenReturn(users);

        when(roleRepository.findByAuthority(role.getAuthority()))
                .thenReturn(Optional.of(role));

        when(userRepository.findByUsername(userName))
                .thenReturn(Optional.empty());

        when(userRepository.save(any()))
                .thenReturn(user);

        when(passwordEncoder.encode(password))
                .thenReturn("encoded_password");
        // Act
        ApplicationUser response = authenticationService.registerUser(userName,password);

        //Assert
        assertEquals(user,response);
        assertEquals(user.getAuthorities(),response.getAuthorities());
    }
    @Test
    public void registerUser_Failed(){
        //Arrange
        Set<Role> roles = new HashSet<>();
        var role = new Role(1,"User");
        roles.add(role);
        String userName ="testUser";
        String password = "testPassword";
        var user =new ApplicationUser(UUID.randomUUID().toString(),
                userName,password,roles);
        var newuser =new ApplicationUser("1","test","password",roles);
        ArrayList<ApplicationUser> users = new ArrayList<>();
        users.add(newuser);

        when(userRepository.findAll())
                .thenReturn(users);

        when(roleRepository.findByAuthority(role.getAuthority()))
                .thenReturn(Optional.of(role));

        when(userRepository.findByUsername(userName))
                .thenReturn(Optional.of(user));


        // Act
        ApplicationUser response = authenticationService.registerUser(userName,password);

        //Assert
        assertEquals(null,response);
    }
    @Test
    public void login_Successful(){
        // Arrange
        String username = "testUser";
        String password = "testPassword";
        ApplicationUser user = new ApplicationUser();
        user.setUsername(username);
        var model = new UsernamePasswordAuthenticationToken(username, password);
        var loginLog = new TimeLog(UUID.randomUUID().toString(),user);
        when(authenticationManager.authenticate(any()))
                .thenReturn(model);

        when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(user));

        when(tokenService.generateJwt(model))
                .thenReturn("token");

        when(timeLogRepository.save(any()))
                .thenReturn(loginLog);

        // Act
        LoginResponseDTO response = authenticationService.loginUser(username, password);

        //Verify
        verify(timeLogRepository, times(1)).save(any());

        // Assert
        assertEquals(user, response.getUser());
        assertEquals("token", response.getJwt());
    }

    @Test
    public void login_Failed(){
        // Arrange
        String username = "username";
        String password = "password";

        when(authenticationManager.authenticate(any())).thenThrow(mock(AuthenticationException.class));

        // Act
        LoginResponseDTO loginResponseDTO = authenticationService.loginUser(username, password);


        // Assert
        assertNull(loginResponseDTO);
    }

    @Test
    public void logOut_Test(){
        //Arrange
        Set<Role> roles = new HashSet<>();
        var role = new Role(1,"User");
        roles.add(role);
        String userName ="testUser";
        String password = "testPassword";
        var user =new ApplicationUser(UUID.randomUUID().toString(),
                userName,password,roles);
        var loginLog = new TimeLog(UUID.randomUUID().toString(),user);
        when(timeLogRepository.findFirstByUserOrderByLoginDateDesc(any()))
                .thenReturn(loginLog);

        when(userRepository.findByUsername(userName)).thenReturn(Optional.of(user));

        loginLog.setLogoutDate(new Date());

        when(timeLogRepository.save(any())).thenReturn(loginLog);

        //Act
        authenticationService.logOut(userName);

        //Verify
        verify(timeLogRepository, times(1)).save(any());
    }




}
