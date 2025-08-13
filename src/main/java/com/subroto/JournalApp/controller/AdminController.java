package com.subroto.JournalApp.controller;

import com.subroto.JournalApp.cache.AppCache;
import com.subroto.JournalApp.entity.User;
import com.subroto.JournalApp.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private AppCache appCache ;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        List<User> all = userServices.getAll();
        if (all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping("/create-admin-user")
    public void createAdmin(@RequestBody User user){
        userServices.saveAdmin(user);
    }


//    for clear the cache data
    @GetMapping("/clear-app-cache")
    public void clearCache(){
        appCache.init();
    }

}
