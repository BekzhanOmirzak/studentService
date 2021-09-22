package com.studentservice.demo.controller;


import com.studentservice.demo.entity.ImageFile;
import com.studentservice.demo.entity.Student;
import com.studentservice.demo.service.AmazonS3Service;
import com.studentservice.demo.service.StudentService;
import com.studentservice.demo.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class RegisterController {

    private final StudentService studentService;
    private final TokenService tokenService;
    private final AmazonS3Service amazonS3Service;

    @PostMapping("/register")
    public String registerStudent(@RequestBody Student student) {
        return studentService.registerStudent(student);
    }

    @GetMapping("/confirm")
    public String confirmToken(@RequestParam String token) {
        return tokenService.confirmTokenService(token);
    }

    @GetMapping("/login")
    public String loginInByEmailAndPassword(@RequestParam String email, @RequestParam String password) {
        return studentService.loginByEmailAndPassword(email, password);
    }

    @PostMapping("/{email}/updatestudent")
    public void updateStudent(@PathVariable("email") String oldEmail, @RequestBody Student student) {
        studentService.updateStudent(oldEmail, student);
    }

    @GetMapping("/{email}")
    public Student getStudentByEmail(@PathVariable("email") String email) {
        return studentService.getStudentByEmail(email);
    }

    @PostMapping("/uploadPhoto")
    public String uploadPhoto(@RequestParam String email, @RequestPart(name = "image") MultipartFile image) {
        studentService.updateImageLink(email, image);
        return "OK";
    }

    @GetMapping("/downloadPhoto")
    public byte[] downloadPhoto(@RequestParam("imagePath") String path, @RequestParam("imageLink") String key) {
        return amazonS3Service.downloadPhoto(path, key);
    }


    @GetMapping("/listPhotos")
    public List<ImageFile> getListOfPhotos(@RequestParam("email") String email) {
        return amazonS3Service.getListOfPhotos(email);
    }
        



}
