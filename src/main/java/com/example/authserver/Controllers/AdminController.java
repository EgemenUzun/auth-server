package com.example.authserver.Controllers;

import com.example.authserver.Entities.ApplicationUser;
import com.example.authserver.Entities.Role;
import com.example.authserver.Service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("${admin.Url}")
@CrossOrigin("*")
public class AdminController {
    @Autowired
    private AuthenticationService authenticationService;
    @GetMapping()
    public String Success()
    {
        return "Admin successfuly logged in";
    }

    @GetMapping("/")
    public List<ApplicationUser> getAllUsers(){
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(2,"User"));
        return  authenticationService.getUserByRoles(roles);
    }

    @GetMapping("/manageUser")
    public ApplicationUser manageUser(@RequestBody ApplicationUser user){
        return authenticationService.manageUser(user);
    }
}
