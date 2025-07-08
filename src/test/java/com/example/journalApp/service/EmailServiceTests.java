package com.example.journalApp.service;

import com.example.journalApp.Service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {

    @Autowired
    private EmailService emailService;

    @Test
    public void sendEmailTest(){
        emailService.sendEmail("pcmshreyansh@gmail.com", "Testing email service", "Hi, Kaise hain aap aaj?");

    }
}
