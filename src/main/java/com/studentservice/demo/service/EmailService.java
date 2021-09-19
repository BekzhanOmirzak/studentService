package com.studentservice.demo.service;


import com.studentservice.demo.repo.EmailSender;
import com.studentservice.demo.repo.StudentRepo;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender {

    private JavaMailSender mailSender;
    private StudentRepo studentRepo;


    @Override
    public void sendEmail(String email, String body, String subject) {

        try {
            MimeMessage mimemessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimemessage, "UTF-8");
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(body, true);
            mailSender.send(mimemessage);
        } catch (MessagingException ex) {
            studentRepo.deleteByEmail(email);
            throw new IllegalArgumentException("Please, provide valid email address");
        }


    }


}
