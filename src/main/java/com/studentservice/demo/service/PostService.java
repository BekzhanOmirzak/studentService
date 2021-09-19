package com.studentservice.demo.service;


import com.studentservice.demo.repo.PostRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepo postRepo;


}
