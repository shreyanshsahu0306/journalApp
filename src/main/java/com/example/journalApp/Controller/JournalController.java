package com.example.journalApp.Controller;

import com.example.journalApp.Model.Journal;
import com.example.journalApp.Service.JournalService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Journal> getEntries(){
        return journalService.getAllEntries();
    }

    @GetMapping("/get/{id}")
    public Optional<Journal> getEntry(@PathVariable ObjectId  id){
        return journalService.getEntryById(id);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteEntry(@PathVariable ObjectId  id){
        journalService.deleteById(id);
        return true;
    }

    @PostMapping("/create")
    public boolean createEntry(@RequestBody Journal myEntry){
        journalService.createEntry(myEntry);
        return true;
    }

    @PutMapping("/update/{id}")
    public Journal updateEntry(@RequestBody Journal myEntry, @PathVariable ObjectId  id){
        return journalService.updateEntry(myEntry, id);
    }

}
