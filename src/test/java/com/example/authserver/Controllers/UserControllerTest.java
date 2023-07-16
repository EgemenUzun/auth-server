package com.example.authserver.Controllers;

import com.example.authserver.Entities.ApplicationUser;
import com.example.authserver.Models.RegistrationDTO;
import com.example.authserver.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testChangePassword() {
        // Arrange
        RegistrationDTO registrationDTO = new RegistrationDTO("username", "old_password");
        ApplicationUser user = new ApplicationUser();
        user.setUsername("username");

        // Mocking userService.changePassword() method to return the updated user
        when(userService.changePassword(registrationDTO)).thenReturn(user);

        // Act
        ApplicationUser updatedUser = userController.changePassword(registrationDTO);

        // Assert
        assertEquals(user, updatedUser);
    }
    @Test
    public void testSuccess() {
        // Act
        String response = userController.Success();

        // Assert
        assertEquals("User controller ", response);
    }

}