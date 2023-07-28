package com.example.authserver.Controllers;

import com.example.authserver.Entities.ApplicationUser;
import com.example.authserver.Models.JwtValidResponse;
import com.example.authserver.Models.LoginResponseDTO;
import com.example.authserver.Models.RegistrationDTO;
import com.example.authserver.Service.AuthenticationService;
import com.example.authserver.Service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${auth.Url}")
@CrossOrigin("*")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    TokenService tokenService;
    @PostMapping("/register")
    public ResponseEntity<ApplicationUser> registerUser(@RequestBody RegistrationDTO body){
        var user = authenticationService.registerUser(body.getUsername(),body.getPassword());
        if(user != null)
            return new ResponseEntity<> (user,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody RegistrationDTO body){
        var model =authenticationService.loginUser(body.getUsername(), body.getPassword());
        if (model != null){
            return new ResponseEntity<> (model,HttpStatus.OK);
        }else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);


    }

    @PostMapping("/isTokenValid")
    public boolean isValid(@RequestBody JwtValidResponse token){
        return tokenService.isTokenValid(token.getJwt());
    }

    @GetMapping("/getRole")
    public String getRoles(@RequestBody JwtValidResponse token){
        return  tokenService.getRoles(token.getJwt());
    }

    @PostMapping("/logout/{username}")
    public void logOut(@PathVariable String username){
        authenticationService.logOut(username);
    }

    @PostMapping("/delete/{username}")
    public void deleteUser(@PathVariable String username){
        authenticationService.deletUser(username);
    }

}
