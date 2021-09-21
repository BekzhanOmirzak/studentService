package com.studentservice.demo.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String lastName;

    @JoinColumn(unique = true)
    private String email;
    private String password;
    private String major;
    private String phoneNumber;
    private String university;
    private String imageLink;
    private String imagePath;

    private boolean enabled = false;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "student", cascade = CascadeType.REMOVE)
    private List<Post> posts;


}
