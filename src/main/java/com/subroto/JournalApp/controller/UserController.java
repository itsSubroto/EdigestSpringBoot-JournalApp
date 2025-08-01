package com.subroto.JournalApp.controller;

import com.subroto.JournalApp.entity.User;
import com.subroto.JournalApp.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserServices userServices;

//    Get all user
    @GetMapping
    public List<User> getAllUsers(){
        return userServices.getAll();
    }

//    create user
    @PostMapping
    public boolean createUser(@RequestBody User user){
        userServices.saveEntry(user);
        return true;
    }

//    update user
    @PutMapping("/{username}")
    public ResponseEntity<?> updateUser(@RequestBody User user,@PathVariable String  username){
        User userInDb = userServices.findByUserName(username);
        if (userInDb != null){
            userInDb.setUsername(user.getUsername());
            userInDb.setPassword(user.getPassword());
            userServices.saveEntry(userInDb);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
