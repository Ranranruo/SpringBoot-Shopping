package com.create.shop.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Getter
@Setter
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String displayName;
    @Column(nullable = false)
    private String joindate;
    public void setUsername(String username){
        if(username.matches(" "))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이름의 공백은 못들어간다.");
        this.username = username;
    }

    @PrePersist
    public void setDate(){
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        this.joindate = format.format(today);
    }
}
