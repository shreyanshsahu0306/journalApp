package com.example.journalApp.Controller;

import com.example.journalApp.Model.Journal;
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
@RequestMapping("/journal")
public class JournalController {

    @Autowired
    private JournalService journalService;

    @Autowired
    private UserService userService;

    //get all journal entries: /journal/get
    @GetMapping("/get")
    public ResponseEntity<List<Journal>> getEntries(){
        return new ResponseEntity<>(journalService.getAllEntries(), HttpStatus.OK);
    }

    //get all Journal entries for a user: /journal/get/userName
    @GetMapping("/get/{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String  userName){
        User user = userService.findUserByUserName(userName);
        List<Journal> allEntries =  user.getJournalEntries();
        if(allEntries!=null){
            return new ResponseEntity<>(allEntries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //delete Journal entry for a user: /journal/delete/userName/id
    @DeleteMapping("/delete/{userName}/{id}")
    public ResponseEntity<Journal> deleteEntry(@PathVariable ObjectId  id,
                                               @PathVariable String userName){
        journalService.deleteEntry(id, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //create Journal entries for a user: /journal/create/userName
    @PostMapping("/create/{userName}")
    public ResponseEntity<Journal> createEntry(@RequestBody Journal myEntry,
                                               @PathVariable String userName){
        try{
            journalService.createEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //updating journal entry for a user: /journal/update/userName/id
    @PutMapping("/update/{userName}/{id}")
    public ResponseEntity<Journal> updateEntry(@RequestBody Journal myEntry,
                                               @PathVariable ObjectId  id,
                                               @PathVariable String userName){
        Journal old = journalService.getEntryById(id).orElse(null);
        if(old!=null){
           Journal updated = journalService.updateEntry(myEntry, id);
            return new ResponseEntity<>(updated,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
