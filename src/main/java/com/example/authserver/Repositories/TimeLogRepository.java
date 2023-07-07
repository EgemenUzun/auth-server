package com.example.authserver.Repositories;

import com.example.authserver.Entities.ApplicationUser;
import com.example.authserver.Entities.TimeLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeLogRepository extends JpaRepository<TimeLog,String> {
    TimeLog findFirstByUserOrderByLoginDateDesc(ApplicationUser user);
}
