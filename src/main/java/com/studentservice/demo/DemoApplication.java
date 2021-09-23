package com.studentservice.demo;

import com.studentservice.demo.entity.Post;
import com.studentservice.demo.entity.Student;
import com.studentservice.demo.repo.StudentRepo;
import com.studentservice.demo.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@Slf4j
public class DemoApplication {

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private PostService postService;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void saveFirstStudent() {
        Student student = new Student();
        student.setEmail("bekjan1");
        student.setEnabled(true);
        student.setPassword("1234");
        Student student1 = new Student();
        student1.setEmail("bekjan2");
        student1.setEnabled(true);
        student1.setPassword("1234");
        Student student2 = new Student();
        student2.setEmail("bekjan3");
        student2.setEnabled(true);
        student2.setPassword("1234");
        Student student3 = new Student();
        student3.setEmail("bekjan5");
        student3.setEnabled(true);
        student3.setPassword("1234");
        Student student4 = new Student();
        student4.setEmail("bekjan6");
        student4.setEnabled(true);
        student4.setPassword("1234");
        studentRepo.save(student4);
        studentRepo.save(student);
        studentRepo.save(student1);
        studentRepo.save(student2);
        studentRepo.save(student3);


        List<Post> posts = new ArrayList<>(Arrays.asList(
                new Post("Subject 1", "Content 1"),
                new Post("Subject 2", "Content 2"),
                new Post("Subject 3", "Content 3"),
                new Post("Subject 4", "Content 4"),
                new Post("Subject 5", "Content 5")
        ));

        posts.forEach(post -> {
            postService.createPost("bekjan1", post, null);
        });


    }


}
