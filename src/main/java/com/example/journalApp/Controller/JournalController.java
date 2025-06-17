package com.example.journalApp.Controller;

import com.example.journalApp.Model.Journal;
import com.example.journalApp.Model.User;
import com.example.journalApp.Service.JournalService;
import com.example.journalApp.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalController {

    @Autowired
    private JournalService journalService;

    @Autowired
    private UserService userService;

    @GetMapping("/get/{myId}")
    public ResponseEntity<?> getEntries(@PathVariable ObjectId myId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userService.findUserByUserName(userName);
        List<Journal> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
       if(!collect.isEmpty()){
           Optional<Journal> journalEntry = journalService.getEntryById(myId);
           if(journalEntry.isPresent()){
               return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
           }
       }
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAllJournalEntriesOfUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userService.findUserByUserName(userName);
        List<Journal> allEntries =  user.getJournalEntries();
        if(allEntries!=null){
            return new ResponseEntity<>(allEntries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{myId}")
    public ResponseEntity<Journal> deleteEntry(@PathVariable ObjectId  myId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        boolean removed = journalService.deleteEntry(myId, userName);
        if(removed){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<Journal> createEntry(@RequestBody Journal myEntry){
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userName = auth.getName();
            journalService.createEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{myId}")
    public ResponseEntity<Journal> updateEntry(@RequestBody Journal myEntry,
                                               @PathVariable ObjectId  myId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userService.findUserByUserName(userName);
        List<Journal> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            Optional<Journal> journalEntry = journalService.getEntryById(myId);
            if(journalEntry.isPresent()){
                Journal updated = journalService.updateEntry(myEntry, myId);
                return new ResponseEntity<>(updated,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

}
