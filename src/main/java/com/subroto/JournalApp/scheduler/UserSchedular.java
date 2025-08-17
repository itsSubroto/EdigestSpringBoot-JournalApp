package com.subroto.JournalApp.scheduler;

import com.subroto.JournalApp.cache.AppCache;
import com.subroto.JournalApp.entity.JournalEntity;
import com.subroto.JournalApp.entity.User;
import com.subroto.JournalApp.repository.UserRepositoryImp;
import com.subroto.JournalApp.services.EmailService;
import com.subroto.JournalApp.services.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserSchedular {

    @Autowired
    private UserRepositoryImp userRepositoryImp;

    @Autowired
    private EmailService emailService ;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService ;

    @Autowired
    private AppCache appCache ;


    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUserAndSendSAMail(){
        List<User> users = userRepositoryImp.getUserForSA();
        for(User user:users){
            List<JournalEntity> journalEntries = user.getJournalEntries();


            List<String> filetedEntries = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getDesc()).collect(Collectors.toList());

            String entry = String.join(" ", filetedEntries);
            String sentiment = sentimentAnalysisService.getSentiment();

            emailService.sendEmail(user.getEmail(),"Sentiment for last 7 days",sentiment);
        }




    }


    //        Write a Cron for run the AppCache for each 10 min

    @Scheduled(cron = "0 0/10 * 1/1 * ?")
    public void clearAppcache(){
        appCache.init();
    }
}
