package com.example.authserver.Controllers;

import com.example.authserver.Entities.ApplicationUser;
import com.example.authserver.Entities.Role;
import com.example.authserver.Models.LogoutDTO;
import com.example.authserver.Models.RegistrationDTO;
import com.example.authserver.Service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("${auth.Url}")
@CrossOrigin("*")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationDTO body){
        var user = authenticationService.registerUser(body.getUsername(),body.getPassword());
        if(user != null)
            return new ResponseEntity<> (user,HttpStatus.OK);
        else
            return new ResponseEntity<>("User Exist",HttpStatus.BAD_REQUEST);

    }
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> loginUser(@RequestBody RegistrationDTO body){
        var model =authenticationService.loginUser(body.getUsername(), body.getPassword());
        if (model!= null)
            return new ResponseEntity<> (model, HttpStatus.OK);
        return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/logout")
    public void logOut(@RequestBody LogoutDTO body){
        authenticationService.logOut(body.getUserName(),body.getJwt());


    }

}
