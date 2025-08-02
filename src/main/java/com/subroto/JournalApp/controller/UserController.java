package com.subroto.JournalApp.controller;

import com.subroto.JournalApp.entity.User;
import com.subroto.JournalApp.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserServices userServices;


    //No need of this controller anymore; Because any user can't see other users.
    // -----------------------------------------------------------------------------

    //    Get all user
    //    @GetMapping
    //    public List<User> getAllUsers(){
    //        return userServices.getAll();
    //    }

    // -----------------------------------------------------------------------------



//    update user
    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();


        User userInDb = userServices.findByUserName(userName);
        userInDb.setUsername(user.getUsername());
        userInDb.setPassword(user.getPassword());
        userServices.saveEntry(userInDb);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
