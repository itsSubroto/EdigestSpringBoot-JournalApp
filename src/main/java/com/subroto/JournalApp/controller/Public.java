package com.subroto.JournalApp.controller;

import com.subroto.JournalApp.entity.User;
import com.subroto.JournalApp.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class Public {

    @Autowired
    private UserServices userServices ;


    @GetMapping("/health-checker")
    public String healthChecker(){
        return "Ok!";
    }


    //    create user
    @PostMapping("/create-user")
    public boolean createUser(@RequestBody User user){
        userServices.saveNewUser(user);
        return true;
    }
}
