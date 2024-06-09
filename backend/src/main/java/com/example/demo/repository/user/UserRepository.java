package com.example.demo.repository.user;

import com.example.demo.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<Player, Integer> {

   @Query("select a from Account a where a.username=:username and a.password=:pass")
   Account checkAcc(String username, String pass);

   @Query("select p from Player p where p.accountID=:id")
   Player getUser(int id);

   @Query("select a.username from Account a where a.username=:username")
   Account checkUser(String username);
}
