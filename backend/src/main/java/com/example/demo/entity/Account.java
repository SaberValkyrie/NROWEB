package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int id;

    @Column(name = "username")
    public String username;

    @Column(name = "password")
    public String password;

    @Column(name = "ban")
    public boolean ban;

    @Column(name = "is_admin")
    public boolean is_admin;

    @Column(name = "ip_address")
    public String ip;

    @Column(name = "thoi_vang")
    public int sodu;

    @Column(name = "token")
    public String token;

    @Column(name = "code")
    public String code;
    public Account() {

    }
}
