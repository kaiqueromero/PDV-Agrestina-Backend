package com.agrestina.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

        public void sendEmail(String subject, String to, String composeEmail) {
            try {
            var email = new SimpleMailMessage();
            email.setFrom("kaique.eliasbr729@gmail.com");
            email.setSubject(subject);
            email.setTo(to);
            email.setText(composeEmail);
            emailSender.send(email);
                System.out.println("Enviando email!");
                System.out.println(composeEmail);

            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao enviar email!", e);
            }
        }

}

