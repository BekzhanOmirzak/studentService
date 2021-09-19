package com.studentservice.demo.service;


import com.studentservice.demo.entity.ConfirmationToken;
import com.studentservice.demo.exception.ApiRequestException;
import com.studentservice.demo.repo.EmailSender;
import com.studentservice.demo.repo.StudentRepo;
import com.studentservice.demo.repo.TokenRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class TokenService {

    private final TokenRepo tokenRepo;
    private final EmailSender emailSender;
    private final StudentRepo studentRepo;


    public void save(ConfirmationToken confirmationToken) {
        tokenRepo.save(confirmationToken);
        emailSender.sendEmail(confirmationToken.getStudent_id().getEmail(), generateMessage(confirmationToken.getToken()), "Click here to enable your email...");
    }


    private String generateMessage(String token) {
        String link = "https://service-student.herokuapp.com/api/v1/confirm?token=" + token;
        return "<h4>This is your confirmation link </h4>" +
                "<a href=" + link + ">Click here to confirm your email</a>";
    }

    public String confirmTokenService(String token) {
        ConfirmationToken confirmationToken = tokenRepo.findByToken(token).orElseThrow(() ->
                new ApiRequestException("Token not found"));
        if (confirmationToken.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new ApiRequestException("token has already expired...");
        }
        tokenRepo.confirmToken(token);
        studentRepo.enableStudent(confirmationToken.getStudent_id().getEmail());
        return "success";
    }







}
