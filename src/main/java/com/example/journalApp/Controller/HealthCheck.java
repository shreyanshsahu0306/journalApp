package com.example.journalApp.Controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkApp")
public class HealthCheck {

    @GetMapping("/journal")
    public String hello(){
        return "Journal app running";
    }
}
