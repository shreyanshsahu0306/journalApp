package com.example.journalApp.Service;

import com.example.journalApp.Model.Journal;
import com.example.journalApp.Model.User;
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

    @Autowired
    private UserService userService;

    public List<Journal> getAllEntries(){
        return journalRepo.findAll();
    }

    public Optional<Journal> getEntryById(ObjectId id) {
        return journalRepo.findById(id);
    }

    public void deleteEntry(ObjectId  id, String userName){
        User user = userService.findUserByUserName(userName);
        user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        userService.createEntry(user);
        journalRepo.deleteById(id);
    }

    public void createEntry(Journal journalEntry, String userName){
        User user = userService.findUserByUserName(userName);
        journalEntry.setDate(LocalDateTime.now());
        Journal saved = journalRepo.save(journalEntry);
        user.getJournalEntries().add(saved);
        userService.createEntry(user);
    }

    public Journal updateEntry(Journal newEntry, ObjectId  id){
        Journal journal =journalRepo.findById(id).orElse(null);
        journal.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals("") ? newEntry.getTitle(): journal.getTitle());
        journal.setDescription(newEntry.getDescription()!=null && !newEntry.getDescription().equals("") ? newEntry.getDescription(): journal.getDescription());
        journalRepo.save(journal);
        return journal;

    }
}
