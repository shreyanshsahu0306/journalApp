package com.example.journalApp.Service;

import com.example.journalApp.Model.Journal;
import com.example.journalApp.Model.User;
import com.example.journalApp.Repo.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<User> getAllEntries(){
        return userRepo.findAll();
    }

    public Optional<User> getEntryById(ObjectId id) {
        return userRepo.findById(id);
    }

    public void deleteById(ObjectId  id){
        userRepo.deleteById(id);
    }

    public void createEntry(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setDate(LocalDateTime.now());
        user.setRoles(Arrays.asList("USER"));
        userRepo.save(user);
    }

    public void saveEntry(User user){
        user.setDate(LocalDateTime.now());
        userRepo.save(user);
    }

    public User findUserByUserName(String userName){
        return userRepo.findUserByUserName(userName);
    }

    public void deleteUserByUserName(String userName){
        userRepo.deleteUserByUserName(userName);
    }

    public void saveAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setDate(LocalDateTime.now());
        user.setRoles(Arrays.asList("USER", "ADMIN"));
        userRepo.save(user);
    }
    /*public Journal updateEntry(Journal newEntry, ObjectId  id){
        Journal journal =journalRepo.findById(id).orElse(null);
        journal.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals("") ? newEntry.getTitle(): journal.getTitle());
        journal.setDescription(newEntry.getDescription()!=null && !newEntry.getDescription().equals("") ? newEntry.getDescription(): journal.getDescription());
        journalRepo.save(journal);
        return journal;

    }*/
}
