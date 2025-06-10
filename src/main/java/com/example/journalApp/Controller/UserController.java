package com.example.journalApp.Controller;

import com.example.journalApp.Model.User;
import com.example.journalApp.Service.JournalService;
import com.example.journalApp.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<User> getAll(){
        return userService.getAllEntries();
    }

    @GetMapping("/id/{userId}")
    public Optional<User> getUser(@PathVariable ObjectId userId){
        return userService.getEntryById(userId);
    }

    @PostMapping("/create")
    public void createUser(@RequestBody User user){
        userService.createEntry(user);
    }

    @PutMapping("/update/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String userName){
        User userIndb = userService.findUserByUserName(userName);
        if(userIndb!=null){
            userIndb.setUserName(user.getUserName());
            userIndb.setPassword(user.getPassword());
            userService.createEntry(userIndb);
            return new ResponseEntity<>(userIndb, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable ObjectId id){
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

