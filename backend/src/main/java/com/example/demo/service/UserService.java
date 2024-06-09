package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.user.*;
import com.example.demo.support.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;

    public Account authenticate(Account loginRequest) {
        Account acc = userRepository.checkAcc(loginRequest.username, Util.md5(loginRequest.password));
        return acc;
    }

    public void saveAccount(Account account){
        accountRepository.save(account);
    }

    public List<Player> getAllUser() {
        return userRepository.findAll();
    }

    public Player getUserByAccount(int id) {
        return userRepository.getUser(id);
    }

    public Account checkUserName(String username) {
        return userRepository.checkUser(username);
    }
}
