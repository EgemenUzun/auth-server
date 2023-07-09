package com.example.authserver.Service;

import com.example.authserver.Entities.ApplicationUser;
import com.example.authserver.Entities.TimeLog;
import com.example.authserver.Models.LoginResponseDTO;
import com.example.authserver.Entities.Role;
import com.example.authserver.Models.LogoutDTO;
import com.example.authserver.Repositories.RoleRepository;
import com.example.authserver.Repositories.TimeLogRepository;
import com.example.authserver.Repositories.UserRepository;
import jdk.jfr.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Expression;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.Temporal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TimeLogRepository timeLogRepository;

    public ApplicationUser registerUser(String username, String password){
        Role userRole ;
        // Define first user as an admin
        userRole = userRepository.findAll().size()==0 ? roleRepository.findByAuthority("Admin").get()
                : roleRepository.findByAuthority("User").get();
        if(!userRepository.findByUsername(username).isPresent()){
            String encodedPassword = passwordEncoder.encode(password);

            Set<Role> authorities = new HashSet<>();

            authorities.add(userRole);

            return userRepository.save(new ApplicationUser(UUID.randomUUID().toString(), username, encodedPassword, authorities));
        }

       return null;
    }

    public LoginResponseDTO loginUser(String username, String password){

        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            var user = userRepository.findByUsername(username).get();
            var loginLog = new TimeLog(UUID.randomUUID().toString(),user);
            String token = tokenService.generateJwt(auth);


            timeLogRepository.save(loginLog);
            return new LoginResponseDTO(user, token);

        } catch(AuthenticationException e){
            return null;
        }
    }
    public LogoutDTO logOut(String user, String token){
        var timeLog =timeLogRepository.findFirstByUserOrderByLoginDateDesc(userRepository.findByUsername(user).get());
        timeLog.setLogoutDate(new Date());
        timeLogRepository.save(timeLog);
        return new LogoutDTO(token,user);
    }

}
