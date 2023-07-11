package com.example.authserver.Service;

import com.example.authserver.Entities.ApplicationUser;
import com.example.authserver.Entities.Role;
import com.example.authserver.Models.RegistrationDTO;
import com.example.authserver.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user is not valid"));
    }
    public ApplicationUser changePassword(RegistrationDTO body){
        var user = userRepository.findByUsername(body.getUsername()).get();
        user.setPassword(encoder.encode(body.getPassword()));
        userRepository.save(user);
        return user;
    }

}
