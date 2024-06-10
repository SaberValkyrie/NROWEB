package com.example.demo.repository.user;

import com.example.demo.entity.Player;
import com.example.demo.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface PostRepository extends JpaRepository<Post, Integer> {

        @Query("select p from Post p where p.type=:status order by p.createAt desc ")
        List<Post> getByStatus(byte status);
    }
