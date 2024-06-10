package com.example.demo.controller;

import com.example.demo.dto.ResponseOpject;
import com.example.demo.entity.Post;
import com.example.demo.service.UserService;
import com.example.demo.support.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private UserService userService;
    @GetMapping("/getPost/{status}")
    public ResponseEntity<ResponseOpject> getPost(@PathVariable byte status) {
        String txt = status == 1 ? "Báo Lỗi" : (status == 2 ? "Tố Cáo" : (status == 3 ? "Góp ý" : "Không Có"));
        List<Post> list = userService.getPostByStatus(status);
        if (status == 0){
            list = userService.getbasicPost();
        }
        return Util.checkStatusRes(HttpStatus.OK, "Đã tìm được " + list.size() + " bài viết với trạng thái " + txt, list);
    }
}
