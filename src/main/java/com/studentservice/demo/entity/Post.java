package com.studentservice.demo.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String subject;
    private String content;
    private String imageLink;
    private String imagePath;

    @ManyToOne(fetch = FetchType.EAGER)
    private Student student;

    private LocalDateTime createdAt;

    public Post(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }
}
