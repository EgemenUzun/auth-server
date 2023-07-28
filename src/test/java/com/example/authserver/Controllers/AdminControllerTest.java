package com.example.authserver.Controllers;

import com.example.authserver.Entities.ApplicationUser;
import com.example.authserver.Entities.Role;
import com.example.authserver.Service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AdminControllerTest {

    @InjectMocks
    private AdminController adminController;

    @Mock
    private AuthenticationService authenticationService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void success() {
        // Act
        String response = adminController.Success();

        // Assert
        assertEquals("Admin successfuly logged in", response);
    }
    @Test
    public void getAllUser_Test(){
        //Arrange
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(2,"User"));
        List<ApplicationUser> model = new ArrayList<>();
        model.add(new ApplicationUser("id","username","password",roles));

        when(authenticationService.getUserByRoles(any())).thenReturn(model);

        //Act
        var response = adminController.getAllUsers();

        //Assert
        assertEquals(model,response);
    }

    @Test
    public void manageUser(){
        //Arrange
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(2,"User"));
        ApplicationUser model =new ApplicationUser("id","username","password",roles);

        when(authenticationService.manageUser(any())).thenReturn(model);

        //Act
        var response = adminController.manageUser(model);

        //Assert
        assertEquals(model,response);
    }
}