package com.example.demo.dto;

import com.example.demo.entity.Player;
import lombok.Data;

@Data
public class AccountResponse {
    public String token;
    public Player player;
    public String pasword;
}
