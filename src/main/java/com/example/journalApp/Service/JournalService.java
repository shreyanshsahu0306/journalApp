package com.example.journalApp.Service;

import com.example.journalApp.Model.Journal;
import com.example.journalApp.Repo.JournalRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalService {

    @Autowired
    private JournalRepo journalRepo;

    public List<Journal> getAllEntries(){
        return journalRepo.findAll();
    }

    public Optional<Journal> getEntryById(ObjectId id) {
        return journalRepo.findById(id);
    }

    public void deleteById(ObjectId  id){
        journalRepo.deleteById(id);
    }

    public void createEntry(Journal journalEntry){
        journalEntry.setDate(LocalDateTime.now());
        journalRepo.save(journalEntry);
    }

    public Journal updateEntry(Journal newEntry, ObjectId  id){
        Journal oldEntry =journalRepo.findById(id).orElse(null);
        if(oldEntry!=null){
            oldEntry.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals("") ? newEntry.getTitle(): oldEntry.getTitle());
            oldEntry.setDescription(newEntry.getDescription()!=null && !newEntry.getDescription().equals("") ? newEntry.getDescription(): oldEntry.getDescription());
        journalRepo.save(oldEntry);
        }
        return oldEntry;

    }
}
