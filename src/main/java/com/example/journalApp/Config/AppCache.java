package com.example.journalApp.Config;

import com.example.journalApp.Model.ConfigJournalAppEntity;
import com.example.journalApp.Repo.ConfigRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class AppCache {

    public enum keys{
        WEATHER_API;
    }

    public HashMap<String, String> APP_CACHE;

    @Autowired
    private ConfigRepo configRepo;

    @PostConstruct
    public void init(){
        APP_CACHE = new HashMap<>();
        List<ConfigJournalAppEntity> all = configRepo.findAll();
        for(ConfigJournalAppEntity c : all){
            APP_CACHE.put(c.getKey(), c.getValue());
        }
    }

}
