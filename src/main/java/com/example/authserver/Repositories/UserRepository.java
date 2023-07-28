package com.example.authserver.Repositories;

import com.example.authserver.Entities.ApplicationUser;
import com.example.authserver.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Repository
public interface UserRepository extends JpaRepository<ApplicationUser, String> {
    Optional<ApplicationUser> findByUsername(String username);

    List<ApplicationUser> findAllByAthoritiesIn(Set<Role> athorities);


}
