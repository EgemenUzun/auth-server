package com.example.authserver.Entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "time_log")
@Data
public class TimeLog {
    @Id
    @Column(name = "log_id")
    private String id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private ApplicationUser user;
    @CreationTimestamp
    @Column(name = "login_date")
    private Date loginDate;
    @UpdateTimestamp
    @Column(name = "logout_date")
    private Date logoutDate;

    public TimeLog(String id, ApplicationUser user) {
        this.id = id;
        this.user = user;
    }

    public TimeLog() {

    }
}
