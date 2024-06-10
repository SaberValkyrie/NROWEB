package com.example.demo.entity;

import lombok.Data;
import org.apache.catalina.User;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "post")
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public long id;

    @Column(name = "title")
    public String title;

    @ManyToOne
    @JoinColumn(name = "player")
    public Player player;

    @Column(name = "create_at")
    public Timestamp createAt;

    @Column(name = "type")
    public byte type; //1: bao loi , 2:to cao , 3:gop y

    @Column(name = "is_lock")
    public boolean islock;
}
