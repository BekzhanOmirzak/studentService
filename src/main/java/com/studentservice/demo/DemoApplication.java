package com.studentservice.demo;

import com.studentservice.demo.entity.Student;
import com.studentservice.demo.repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class DemoApplication {

    @Autowired
    private StudentRepo studentRepo;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void saveFirstStudent() {
        Student student = new Student();
        student.setEmail("bekjan");
        student.setEnabled(true);
        student.setPassword("1234");
        studentRepo.save(student);
    }




}
