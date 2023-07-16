package com.example.authserver.Service;

import com.example.authserver.Entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.security.PublicKey;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TokenService {
    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private JwtDecoder jwtDecoder;

    public String generateJwt(Authentication auth){

        Instant now = Instant.now();
        Instant expiration = now.plus(Duration.ofDays(7));

        String scope = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .subject(auth.getName())
                .claim("roles", scope)
                .expiresAt(expiration)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
    public boolean isTokenValid(String token)  {
        try {
            return !jwtDecoder.decode(token).getExpiresAt().isBefore(Instant.now());
        }catch (Exception e){return  false;}

    }
    public String getRoles(String token){
        return jwtDecoder.decode(token).getClaim("roles");
    }


}
