package com.example.journalApp.Service;

import com.example.journalApp.Model.Journal;
import com.example.journalApp.Model.User;
import com.example.journalApp.Repo.JournalRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public boolean deleteEntry(ObjectId  myid, String userName){
        boolean removed = false;
        try{
            User user = userService.findUserByUserName(userName);
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(myid));
            if(removed){
                userService.saveEntry(user);
                journalRepo.deleteById(myid);
            }
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error occurred while deleting the entry: ",e);
        }
        return removed;
    }

    @Transactional
    public void createEntry(Journal journalEntry, String userName){
        try{
            User user = userService.findUserByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            Journal saved = journalRepo.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveEntry(user);
        }catch(Exception e){
            System.out.println(e);
            throw new RuntimeException("An error has occured while saving the entry.",e);
        }

    }

    public Journal updateEntry(Journal journal, ObjectId  myId){
        Journal old =journalRepo.findById(myId).orElse(null);
        old.setTitle(journal.getTitle()!=null && !journal.getTitle().equals("") ? journal.getTitle(): old.getTitle());
        old.setDescription(journal.getDescription()!=null && !journal.getDescription().equals("") ? journal.getDescription(): old.getDescription());
        journalRepo.save(old);
        return old;

    }
}

