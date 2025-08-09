package com.subroto.JournalApp.services;

import com.subroto.JournalApp.entity.User;
import com.subroto.JournalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class UserServices {

    @Autowired
    private UserRepository userRepository;

    //Create (No Encryption)
    public void saveUser(User user){
        try {

            userRepository.save(user);

        } catch (Exception e) {
            log.error("Exception", e);
        }
    }

    // Creating instance of Paasword Encoder
    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();



    //Create User By BCryping the password(WITH ENCRYPTION)
    public void saveNewUser(User user){
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);

        } catch (Exception e) {
            log.error("Exception", e);
        }
    }

    // To create a new admin
    public boolean saveAdmin(User user){
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER","ADMIN"));
            userRepository.save(user);
            return  true;
        } catch (Exception e) {
            log.error("Exception", e);
            return false;
        }
    }


    
//    Read All
    public List<User> getAll(){
        return userRepository.findAll();
    }
    
//    Read By Id
    public Optional<User> findById(ObjectId id){
        return userRepository.findById(id);
    }

//    Delete By ID
    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }

//    find the user by userName
    public User findByUserName(String username){
        return userRepository.findByUsername(username);
    }


}


