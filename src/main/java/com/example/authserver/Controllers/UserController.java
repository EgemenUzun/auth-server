package com.example.authserver.Controllers;

import com.example.authserver.Entities.ApplicationUser;
import com.example.authserver.Entities.TimeLog;
import com.example.authserver.Models.RegistrationDTO;
import com.example.authserver.Service.AuthenticationService;
import com.example.authserver.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpRequest;

@RestController
@RequestMapping("${user.Url}")
@CrossOrigin("*")
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping
    public String Success()
    {
        return "User controller ";
    }

    @PutMapping("/changePassword")
    public ApplicationUser changePassword(@RequestBody RegistrationDTO body){
        return userService.changePassword(body);
    }

}

