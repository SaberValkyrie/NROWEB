package com.example.demo.entity;

import javax.persistence.*;

import lombok.Data;


@Entity
@Table(name = "player")
@Data
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int id;

    @Column(name = "items_body")
    public String itemsBody;

    @Column(name = "gender")
    public byte gender;

    @Column(name = "data_inventory")
    public String inventory;

    @Column(name = "name")
    public String name;

    @Column(name = "account_id")
    public int accountID;

    @Column(name = "data_point")
    public String point;



}