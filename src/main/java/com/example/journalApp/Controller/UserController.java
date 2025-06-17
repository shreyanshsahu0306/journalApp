package com.example.journalApp.Controller;

import com.example.journalApp.Model.User;
import com.example.journalApp.Service.JournalService;
import com.example.journalApp.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User userIndb = userService.findUserByUserName(userName);
            userIndb.setUserName(user.getUserName());
            userIndb.setPassword(user.getPassword());
            userService.saveEntry(userIndb);
            return new ResponseEntity<>(userIndb, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        userService.deleteUserByUserName(userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

