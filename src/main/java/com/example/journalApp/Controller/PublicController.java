package com.example.journalApp.Controller;


import com.example.journalApp.Model.User;
import com.example.journalApp.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/journal")
    public String hello(){
        return "Journal app running";
    }

    //get all users method to be part of Admin and not public
    @GetMapping("/all")
    public List<User> getAll(){
        return userService.getAllEntries();
    }

    @GetMapping("/id/{userId}")
    public Optional<User> getUser(@PathVariable ObjectId userId){
        return userService.getEntryById(userId);
    }

    @PostMapping("/create-user")
    public void createUser(@RequestBody User user){
        userService.createEntry(user);
    }
}
