package com.subroto.JournalApp.service;

import com.subroto.JournalApp.entity.User;
import com.subroto.JournalApp.repository.UserRepository;
import com.subroto.JournalApp.services.UserDetailsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.bean.override.mockito.MockitoBean;


import static org.mockito.Mockito.*;

@SpringBootTest
public class UserDetailsServiceImplTest {

    @Autowired
    public UserDetailsServiceImpl userDetailsService;


    @MockitoBean
    public UserRepository userRepository;


     @Test
     void loadUserByUsernameTest(){
//        when(userRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(User.builder);
        UserDetails user=userDetailsService.loadUserByUsername("ram");
         Assertions.assertNotNull(user);
    }
}
