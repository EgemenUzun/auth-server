package com.example.authserver.Controllers;

import com.example.authserver.Entities.Role;
import com.example.authserver.Service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${admin.Url}")
@CrossOrigin("*")
public class AdminController {
    @Autowired
    private AuthenticationService authenticationService;
    @GetMapping("/")
    public String Success()
    {
        return "Admin successfuly logged in";
    }
    // TODO can change roles , manage users ,optional(ban users)
}
