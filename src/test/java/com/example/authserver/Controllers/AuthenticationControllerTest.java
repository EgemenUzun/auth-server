package com.example.authserver.Controllers;

import com.example.authserver.Entities.ApplicationUser;
import com.example.authserver.Entities.Role;
import com.example.authserver.Models.JwtValidResponse;
import com.example.authserver.Models.LoginResponseDTO;
import com.example.authserver.Models.RegistrationDTO;
import com.example.authserver.Service.AuthenticationService;
import com.example.authserver.Service.TokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @Mock
    AuthenticationService authenticationService;
    /*@Mock
    TokenService tokenService; */
    /*@Mock
    private AuthenticationManager authenticationManager;*/

    @InjectMocks
    AuthenticationController authenticationController;


    @Test
    public void test_Valid_RegisterUser() {

        RegistrationDTO body = new RegistrationDTO("username", "password");


        when(authenticationService.registerUser(body.getUsername(), body.getPassword())).thenReturn(new ApplicationUser());


        ResponseEntity<ApplicationUser> response = authenticationController.registerUser(body);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
    @Test
    public void test_inValid_RegisterUser() {

        RegistrationDTO body = new RegistrationDTO("egemen", "password");
        /*Set<Role> roles = new HashSet<>();
        roles.add(new Role(1,"Admin"));*/

       // var model = new ApplicationUser("1","username","password",roles);

        when(authenticationService.registerUser(body.getUsername(), body.getPassword())).thenReturn(new ApplicationUser());


        ResponseEntity<ApplicationUser> response = authenticationController.registerUser(body);


        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testLoginUser() {

        RegistrationDTO body = new RegistrationDTO("username", "password");
        authenticationService.registerUser(body.getUsername(), body.getPassword());

        when(authenticationService.loginUser(body.getUsername(), body.getPassword())).thenReturn(new LoginResponseDTO());


        ResponseEntity<LoginResponseDTO> response = authenticationController.loginUser(body);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response);
    }
    /*@Test
    public void isTokenValid(){
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("username", "password"));
        String token = tokenService.generateJwt(auth);
        JwtValidResponse jwt = new JwtValidResponse(token);

        boolean response = authenticationController.isValid(jwt);

        assertTrue(response);
    }

    @Test
    public void getRole(){
        JwtValidResponse jwt = new JwtValidResponse(authenticationService.loginUser("egemen","password").getJwt());
        String response = authenticationController.getRoles(jwt);

        assertEquals("Admin",response);
    }*/
}
