package com.example.authserver.Models;

import com.example.authserver.Entities.ApplicationUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {
    private ApplicationUser user;
    private String jwt;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(ApplicationUser user, String jwt) {
        this.user = user;
        this.jwt = jwt;
    }
}
