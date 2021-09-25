package com.studentservice.demo.controller;


import com.studentservice.demo.entity.Post;
import com.studentservice.demo.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class PostController {


    @Autowired
    private final PostService postService;


    @PostMapping("/createPost")
    public ResponseEntity<Void> createPost(@RequestParam("email") String email,
                                           @RequestParam("subject") String subject,
                                           @RequestParam("content") String content,
                                           @RequestPart("image") MultipartFile file) {
        postService.createPost(email, new Post(subject, content), file);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/randomPosts")
    public ResponseEntity<List<Post>> getRandomPosts(int page) {
        List<Post> posts = postService.getListOfPostsRandomly(page);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/deletePostById/{id}")
    public void deletePostById(@PathVariable("id") Long id) {
        postService.removePostsById(id);
    }




}
