package com.example.journalApp.Scheduler;

import com.example.journalApp.Config.AppCache;
import com.example.journalApp.Enums.Sentiment;
import com.example.journalApp.Model.Journal;
import com.example.journalApp.Model.SentimentData;
import com.example.journalApp.Model.User;
import com.example.journalApp.Repo.UserRepoImpl;
import com.example.journalApp.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class EmailScheduler {

    @Autowired
    private UserRepoImpl userRepoimpl;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AppCache appCache;

    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersAndSendSaMail() {
        List<User> users = userRepoimpl.getUsersForSA();
        for (User user : users) {
            List<Journal> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment, Integer> sentimentCounts = new HashMap<>();
            for (Sentiment sentiment : sentiments) {
                if (sentiment != null)
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
            }
            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for (Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }
            if (mostFrequentSentiment != null) {
                SentimentData sentimentData = SentimentData.builder().email(user.getEmail()).sentiment("Sentiment for last 7 days " + mostFrequentSentiment).build();
                try{
                    //kafkaTemplate.send("weekly-sentiments", sentimentData.getEmail(), sentimentData);
                }catch (Exception e){
                    emailService.sendEmail(sentimentData.getEmail(), "Sentiment for previous week", sentimentData.getSentiment());
                }
            }
        }
    }

    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache() {
        appCache.init();
    }
}
