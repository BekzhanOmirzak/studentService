package com.studentservice.demo.service;


import com.studentservice.demo.entity.ConfirmationToken;
import com.studentservice.demo.entity.Student;
import com.studentservice.demo.exception.ApiRequestException;
import com.studentservice.demo.repo.StudentRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import static org.apache.http.entity.ContentType.*;

@Service
@AllArgsConstructor
public class StudentService {

    private final StudentRepo studentRepo;
    private final TokenService tokenService;
    private final AmazonS3Service amazonS3Service;


    public String registerStudent(Student student) {
        Optional<Student> studentDB = studentRepo.findByEmail(student.getEmail());

        if (studentDB.isPresent())
            return "exist";
        studentRepo.save(student);
        ConfirmationToken confirmToken = new ConfirmationToken();
        confirmToken.setToken(UUID.randomUUID().toString());
        confirmToken.setStudent_id(student);
        confirmToken.setCreatedAt(LocalDateTime.now());
        confirmToken.setExpiredAt(LocalDateTime.now().plusMinutes(15));
        tokenService.save(confirmToken);
        return "success";
    }

    public String loginByEmailAndPassword(String email, String password) {
        Student student = studentRepo.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new ApiRequestException("User can't be found..."));

        if (!student.isEnabled()) {
            return "notEnabled";
        }
        return "success";
    }

    public void updateStudent(String oldEmail, Student student) {
        Student oldStudent = studentRepo.findByEmail(oldEmail)
                .orElseThrow(() -> new ApiRequestException("User can't be found"));
        if (student.getEmail() != null)
            oldStudent.setEmail(student.getEmail());
        oldStudent.setLastName(student.getLastName());
        oldStudent.setImageLink(student.getImageLink());
        oldStudent.setMajor(student.getMajor());
        oldStudent.setName(student.getName());
        oldStudent.setUniversity(student.getUniversity());
        oldStudent.setPhoneNumber(student.getPhoneNumber());
        studentRepo.save(oldStudent);
    }

    public void updateImageLink(String email, MultipartFile file) {

        if (file.isEmpty())
            throw new ApiRequestException("Cannot upload empty file");

        if (!Arrays.asList(IMAGE_PNG.getMimeType(),
                IMAGE_JPEG.getMimeType(),
                IMAGE_GIF.getMimeType(),
                IMAGE_JPEG.getMimeType()).contains(file.getContentType())) {
            throw new ApiRequestException("File upload is not a image");
        }
        Map<String, String> metaData = new HashMap<>();
        metaData.put("Content-Type", file.getContentType());
        metaData.put("Content-Length", String.valueOf(file.getSize()));

        String path = String.format("students/%s/imageLink", email);
        String fileName = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID().toString());

        try {
            amazonS3Service.upload(path, fileName, Optional.of(metaData), file.getInputStream());
        } catch (IOException ex) {
            throw new ApiRequestException("Failed to upload file");
        }

    }


    public Student getStudentByEmail(String email) {
        return studentRepo.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User can't be found"));
    }


}
