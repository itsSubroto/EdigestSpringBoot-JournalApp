package com.subroto.JournalApp.services;

import com.subroto.JournalApp.entity.JournalEntity;
import com.subroto.JournalApp.entity.User;
import com.subroto.JournalApp.repository.JournalEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class JournalEntryServices {

    @Autowired
   private JournalEntityRepository journalEntityRepository;

    @Autowired
    private UserServices userServices ;


    //Create
    // Along with this we have to add @EnableTransactionManagement  at the main application
    @Transactional
    public void saveEntry(JournalEntity journalEntity, String userName){
        try {
            User user= userServices.findByUserName(userName);
            journalEntity.setDate(LocalDateTime.now());
            JournalEntity saved = journalEntityRepository.save(journalEntity);
            user.getJournalEntries().add(saved);
            userServices.saveEntry(user);
        } catch (Exception e) {
            log.error("Exception", e);
            throw new RuntimeException("An  error occurred while saving the entry -> ",e);
        }
    }

//    method overloading for just update
    //Create
    public void saveEntry(JournalEntity journalEntity){
        try {
           journalEntityRepository.save(journalEntity);



        } catch (Exception e) {
            log.error("Exception", e);
        }
    }
    
//    Read All
    public List<JournalEntity> getAll(){
        return journalEntityRepository.findAll();
    }
    
//    Read By Id
    public Optional<JournalEntity> findById(ObjectId id){
        return journalEntityRepository.findById(id);
    }

//    Delete By ID
    public void deleteById(ObjectId id, String userName){
        User user= userServices.findByUserName(userName);
        user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        userServices.saveEntry(user);
        journalEntityRepository.deleteById(id);
    }

}
