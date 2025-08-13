package com.subroto.JournalApp.controller;

import com.subroto.JournalApp.api.response.WeatherResponse;
import com.subroto.JournalApp.entity.User;
import com.subroto.JournalApp.repository.UserRepository;
import com.subroto.JournalApp.services.UserServices;
import com.subroto.JournalApp.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserServices userServices;

    @Autowired
    private UserRepository userRepository ;

    @Autowired
    private WeatherService weatherService ;

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
        userServices.saveNewUser(userInDb);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



    //    Delete user by username
    @DeleteMapping()
    public ResponseEntity<?> deleteUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        userRepository.deleteByUsername(userName);


        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //    API call example >> greetings
    @GetMapping()
    public ResponseEntity<?> greeting(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        WeatherResponse weatherResponse = weatherService.getWeather("Mumbai");
        String greeting="";
        if (weatherResponse != null){
            greeting=" , Weather feels like " + weatherResponse.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("HI "+userName+greeting, HttpStatus.OK);
    }

}
