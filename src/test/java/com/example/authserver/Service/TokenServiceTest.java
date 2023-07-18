package com.example.authserver.Service;

import com.example.authserver.Entities.Role;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.*;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class TokenServiceTest {
    @Mock
    private JwtEncoder jwtEncoder;
    @Mock
    private JwtDecoder jwtDecoder;

    @InjectMocks
    TokenService tokenService;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void generateJwt_Test(){
        // Arrange
        String username = "testuser";
        String password = "testpassword";
        var model = new UsernamePasswordAuthenticationToken(username, password);
        Map<String,Object> header = new HashMap<>();
        header.put("header","head");
        Map<String,Object> claim = new HashMap<>();
        claim.put("roles","Admin");
        Jwt jwt =new Jwt("token",Instant.now()
                ,Instant.now().plus(Duration.ofDays(7)),header,claim);
        when(jwtEncoder.encode(any())).thenReturn(jwt);

        // Act
        String jwtToken = tokenService.generateJwt(model);

        // Assert
        assertNotNull(jwtToken);
        assertEquals(jwt.getTokenValue(),jwtToken);

    }

    @Test
    public void tokenValid_test(){
        //Arrange
        Map<String,Object> header = new HashMap<>();
        header.put("header","head");
        Map<String,Object> claim = new HashMap<>();
        claim.put("roles","Admin");
        Jwt jwt =new Jwt("token",Instant.now()
                ,Instant.now().plus(Duration.ofDays(7)),header,claim);
        when(jwtDecoder.decode(anyString()))
                .thenReturn(jwt);
        //Act
        var response = tokenService.isTokenValid("token");
        //Assert
        assertTrue(response);
        assertEquals(!jwt.getExpiresAt().isBefore(Instant.now()),response);

    }
    @Test
    public void tokenInValid_test(){
        Map<String,Object> header = new HashMap<>();
        header.put("header","head");
        Map<String,Object> claim = new HashMap<>();
        claim.put("roles","Admin");
        Jwt jwt =new Jwt("token",Instant.now()
                ,Instant.now().plus(Duration.ofDays(7)),header,claim);
        when(jwtDecoder.decode(anyString())).thenThrow(new JwtException("Token is expired"));
        //Act
        var response = tokenService.isTokenValid("token_invalid");
        //Assert
        assertFalse(response);
    }
    @Test
    public void getRole_test(){
        Role role = new Role(1,"Admin");
        Map<String,Object> header = new HashMap<>();
        header.put("header","head");
        Map<String,Object> claim = new HashMap<>();
        claim.put("roles","Admin");
        Jwt jwt =new Jwt("token",Instant.now()
                ,Instant.now().plus(Duration.ofDays(7)),header,claim);
        when(jwtDecoder.decode(anyString())).thenReturn(jwt);

        var response = tokenService.getRoles("token");

        assertEquals(jwt.getClaim("roles"),response);
        assertNotNull(response);
    }

}