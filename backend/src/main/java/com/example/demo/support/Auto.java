package com.example.demo.support;

import com.example.demo.entity.*;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalTime;


@Component
public class Auto {
    private final long timeUpdate = 360000;//60p

    @Autowired
    private UserService userService;

        @Scheduled(cron = "0 * * * * *") // Chạy vào mỗi phút
    public void checkVipExpiration() {
    }

    @Scheduled(cron = "0 0 * * * *") // Chạy mỗi giờ
    public void resetShop() {
    }



}