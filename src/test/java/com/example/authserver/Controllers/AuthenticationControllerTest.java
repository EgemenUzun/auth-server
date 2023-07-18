package com.example.authserver.Controllers;

import com.example.authserver.Entities.ApplicationUser;
import com.example.authserver.Entities.Role;
import com.example.authserver.Models.JwtValidResponse;
import com.example.authserver.Models.LoginResponseDTO;
import com.example.authserver.Models.RegistrationDTO;
import com.example.authserver.Service.AuthenticationService;
import com.example.authserver.Service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AuthenticationControllerTest {


    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testRegisterUser_Success() {
        // Arrange
        RegistrationDTO registrationDTO = new RegistrationDTO("username", "password");
        ApplicationUser user = new ApplicationUser();
        user.setUsername("username");
        when(authenticationService.registerUser(registrationDTO.getUsername(), registrationDTO.getPassword()))
                .thenReturn(user);

        // Act
        ResponseEntity<ApplicationUser> response = authenticationController.registerUser(registrationDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testRegisterUser_Failure() {
        // Arrange
        RegistrationDTO registrationDTO = new RegistrationDTO("username", "password");
        when(authenticationService.registerUser(registrationDTO.getUsername(), registrationDTO.getPassword()))
                .thenReturn(null);

        // Act
        ResponseEntity<ApplicationUser> response = authenticationController.registerUser(registrationDTO);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }
    @Test
    public void testLoginUser_Success() {
        // Arrange
        RegistrationDTO registrationDTO = new RegistrationDTO("username", "password");
        ApplicationUser user = new ApplicationUser();
        user.setUsername("username");
        String token = "example_token";
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(user, token);
        when(authenticationService.loginUser(registrationDTO.getUsername(), registrationDTO.getPassword()))
                .thenReturn(loginResponseDTO);

        // Act
        ResponseEntity<LoginResponseDTO> response = authenticationController.loginUser(registrationDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody().getUser());
        assertEquals(token, response.getBody().getJwt());
    }


    @Test
    public void testLoginUser_Failure() {
        // Arrange
        RegistrationDTO registrationDTO = new RegistrationDTO("username", "password");
        when(authenticationService.loginUser(registrationDTO.getUsername(), registrationDTO.getPassword()))
                .thenReturn(null);

        // Act
        ResponseEntity<LoginResponseDTO> response = authenticationController.loginUser(registrationDTO);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testIsValidToken_ValidToken() {
        // Arrange
        JwtValidResponse validTokenResponse = new JwtValidResponse("valid_token");
        when(tokenService.isTokenValid(validTokenResponse.getJwt())).thenReturn(true);

        // Act
        boolean isValidToken = authenticationController.isValid(validTokenResponse);

        // Assert
        assertEquals(true, isValidToken);
    }

    @Test
    public void testIsValidToken_InvalidToken() {
        // Arrange
        JwtValidResponse invalidTokenResponse = new JwtValidResponse("invalid_token");
        when(tokenService.isTokenValid(invalidTokenResponse.getJwt())).thenReturn(false);

        // Act
        boolean isValidToken = authenticationController.isValid(invalidTokenResponse);

        // Assert
        assertEquals(false, isValidToken);
    }

    @Test
    public void testGetRoles_ValidToken() {
        // Arrange
        String token = "valid_token";
        String roles = "ROLE_USER,ROLE_ADMIN";
        JwtValidResponse validTokenResponse = new JwtValidResponse(token);
        when(tokenService.getRoles(validTokenResponse.getJwt())).thenReturn(roles);

        // Act
        String userRoles = authenticationController.getRoles(validTokenResponse);

        // Assert
        assertEquals(roles, userRoles);
    }

    @Test
    public void testLogOut_ValidUsername() {
        // Arrange
        String username = "test_user";

        // Act & Assert
        authenticationController.logOut(username);
    }

}
