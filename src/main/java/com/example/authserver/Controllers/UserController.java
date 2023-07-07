package com.example.authserver.Controllers;

import com.example.authserver.Entities.ApplicationUser;
import com.example.authserver.Entities.TimeLog;
import com.example.authserver.Models.RegistrationDTO;
import com.example.authserver.Service.AuthenticationService;
import com.example.authserver.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;

@RestController
@RequestMapping("${user.Url}")
@CrossOrigin("*")
public class UserController {
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    UserService userService;
    @GetMapping("/")
    public String Success()
    {
        return "User successfuly logged in";
    }

    @PutMapping("/")
    public ApplicationUser changePassword(@RequestBody RegistrationDTO body){
        return userService.changePassword(body);
    }
    @PutMapping("/logout/{userName}")
    public void logOut(@PathVariable String userName){
        authenticationService.logOut(userName);
    }

}
