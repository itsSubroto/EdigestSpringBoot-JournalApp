package com.subroto.JournalApp.service;

import com.subroto.JournalApp.entity.User;
import com.subroto.JournalApp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository ;

    @Test
    @Disabled
    public  void testFindByuserName(){
        User user = userRepository.findByUsername("user1");
        assertTrue(!user.getJournalEntries().isEmpty());
    }


    //multiple test cases in csvSource / csvFile
    // Parameterized test
    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "3,2,5"
    })
    public  void test(int a, int b, int expected){
        assertEquals(expected,a+b);
    }
}
