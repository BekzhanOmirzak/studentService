package com.studentservice.demo.controller;


import com.studentservice.demo.entity.Student;
import com.studentservice.demo.service.StudentService;
import com.studentservice.demo.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class RegisterController {

    private final StudentService studentService;
    private final TokenService tokenService;

    @PostMapping("/register")
    public String registerStudent(@RequestBody Student student) {
        return studentService.registerStudent(student);
    }

    @GetMapping("/confirm")
    public String confirmToken(@RequestParam String token) {
        return tokenService.confirmTokenService(token);
    }

    @GetMapping("/login")
    public String loginInByEmailAndPassword(@RequestParam String email,@RequestParam String password) {
        return studentService.loginByEmailAndPassword(email, password);
    }

    @PostMapping("/updatestudent")
    public void updateStudent(@RequestBody Student student) {
        studentService.updateStudent(student);
    }

    @PostMapping("/uploadimage")
    public void uploadImage(@RequestParam String email, @RequestParam("file") MultipartFile file) {
        studentService.updateImageLink(email,file);
    }




}
