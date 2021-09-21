package com.studentservice.demo.service;


import com.studentservice.demo.entity.Post;
import com.studentservice.demo.entity.Student;
import com.studentservice.demo.exception.ApiRequestException;
import com.studentservice.demo.repo.PostRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepo postRepo;
    private final StudentService studentService;
    private final AmazonS3Service amazonS3Service;


    public void createPost(String email, Post post, MultipartFile file) {

        Student student = studentService.getStudentByEmail(email);

        post.setStudent(student);
        post.setCreatedAt(LocalDateTime.now());

        if (file.isEmpty())
            throw new ApiRequestException("Cannot upload empty file");

        Map<String, String> metaData = new HashMap<>();
        metaData.put("Content-Type", file.getContentType());
        metaData.put("Content-Length", String.valueOf(file.getSize()));


        String path = String.format("%s/%s", "studentservice", "posts");
        String fileName = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID().toString());

        post.setImagePath(path);
        post.setImageLink(fileName);

        try {
            amazonS3Service.upload(path, fileName, Optional.of(metaData), file.getInputStream());
            postRepo.save(post);
        } catch (IOException ex) {
            throw new ApiRequestException("Failed to upload file");
        }

    }




}
