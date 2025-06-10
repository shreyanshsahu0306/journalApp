package com.example.journalApp.Controller;

import com.example.journalApp.Model.Journal;
import com.example.journalApp.Service.JournalService;
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

    @GetMapping("/get")
    public ResponseEntity<List<Journal>> getEntries(){
        return new ResponseEntity<>(journalService.getAllEntries(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Journal> getEntry(@PathVariable ObjectId  id){
        Optional<Journal> journalEntry =  journalService.getEntryById(id);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Journal> deleteEntry(@PathVariable ObjectId  id){
        journalService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/create")
    public ResponseEntity<Journal> createEntry(@RequestBody Journal myEntry){
        try{
            journalService.createEntry(myEntry);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Journal> updateEntry(@RequestBody Journal myEntry, @PathVariable ObjectId  id){
        Journal old = journalService.getEntryById(id).orElse(null);
        if(old!=null){
           Journal updated = journalService.updateEntry(myEntry, id);
            return new ResponseEntity<>(updated,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
