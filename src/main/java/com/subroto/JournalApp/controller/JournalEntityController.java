package com.subroto.JournalApp.controller;

import com.subroto.JournalApp.entity.JournalEntity;
import com.subroto.JournalApp.entity.User;
import com.subroto.JournalApp.services.JournalEntryServices;
import com.subroto.JournalApp.services.UserServices;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntityController {

    @Autowired
    private JournalEntryServices journalEntryServices;

    @Autowired
    private UserServices userServices;

//    private Map<Long,JournalEntity> journalEntries=new HashMap<>();

//    Get journal by userName (READ ALL)
    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User user=userServices.findByUserName(userName);
        List<JournalEntity> all = user.getJournalEntries();
        if(all != null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);

    }

    //    Create
   @PostMapping
   public ResponseEntity<?> createEntry(@RequestBody JournalEntity myEntry){

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            journalEntryServices.saveEntry(myEntry,userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);

        }

    }

    @GetMapping("id/{myId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        // get the User
        User user = userServices.findByUserName(userName);
        List<JournalEntity> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());

        //Check IF the USER has the journal id then only show them the journal
        if(!collect.isEmpty()){
            Optional<JournalEntity> journalEntity = journalEntryServices.findById(myId);
            if(journalEntity.isPresent()){
                return new ResponseEntity<>(journalEntity.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);

    }

//    Update by id
    @PutMapping("id/{myId}")
    public ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId myId,@RequestBody JournalEntity newEntry){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        // get the User
        User user = userServices.findByUserName(userName);
        List<JournalEntity> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());

        //Check IF the USER has the journal id then only show them the journal
        if(!collect.isEmpty()){
            Optional<JournalEntity> journalEntity = journalEntryServices.findById(myId);
            if(journalEntity.isPresent()){
                JournalEntity old = journalEntryServices.findById(myId).orElse(null);
                if(old != null){

                    old.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals("")?newEntry.getTitle(): old.getTitle());
                    old.setDesc(newEntry.getDesc()!=null && !newEntry.getDesc().equals("")?newEntry.getDesc(): old.getDesc());

                    journalEntryServices.saveEntry(old);

                    return new ResponseEntity<>(old,HttpStatus.OK);
                 }
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        Boolean removed = journalEntryServices.deleteById(myId, userName);
        if (removed){
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
