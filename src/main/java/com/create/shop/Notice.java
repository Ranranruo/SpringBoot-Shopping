package com.create.shop;

import jakarta.persistence.*;
import lombok.ToString;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@ToString
public class Notice {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String title;

    @Column(length = 50)
    public String date;
    @PrePersist
    public void setDate(){
        Date today = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(format1.format(today));
        this.date = format1.format(today);
    }
}
