package com.studentservice.demo.service;


import com.studentservice.demo.entity.Post;
import com.studentservice.demo.entity.Student;
import com.studentservice.demo.exception.ApiRequestException;
import com.studentservice.demo.repo.PostRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
@Transactional
public class PostService {

    private final PostRepo postRepo;
    private final StudentService studentService;
    private final AmazonS3Service amazonS3Service;


    public void createPost(String email, Post post, MultipartFile file) {

        Student student = studentService.getStudentByEmail(email);

        post.setStudent(student);
        post.setCreatedAt(LocalDateTime.now());

        postRepo.save(post);
        Map<String, String> metaData = new HashMap<>();
        if (file != null) {
            metaData.put("Content-Type", file.getContentType());
            metaData.put("Content-Length", String.valueOf(file.getSize()));
        }
        System.out.println("Posts id : " + post.getId());
        String path = String.format("%s/%s/%s", "studentservice", "posts", post.getId() + "");
        String fileName = "";

        if (file != null && !file.isEmpty()) {
            post.setImagePath(path);
            fileName = String.format("%s-%s", System.currentTimeMillis() + "", file.getOriginalFilename());
            post.setImageLink(fileName);
        }

        try {
            if (file != null && !file.isEmpty())
                amazonS3Service.upload(path, fileName, Optional.of(metaData), file.getInputStream());

        } catch (IOException ex) {
            throw new ApiRequestException("Failed to upload file");
        }

    }

    public List<Post> getListOfPostsRandomly(int page) {

        List<Post> posts = postRepo.getListOfPostsRandomly(PageRequest.of(page, 30));

        posts.forEach(post -> {
            if (post.getImagePath() != null && post.getImageLink() != null) {
                byte[] content = amazonS3Service.downloadPhoto(post.getImagePath(), post.getImageLink());
                post.setImage_content(content);
            }
        });
        return posts;
    }

    public void removePostsById(Long id) {
        postRepo.deleteById(id);
    }


}
