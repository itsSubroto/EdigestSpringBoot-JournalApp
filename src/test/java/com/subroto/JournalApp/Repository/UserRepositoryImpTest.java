package com.subroto.JournalApp.Repository;

import com.subroto.JournalApp.repository.UserRepositoryImp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryImpTest {

    @Autowired
    private UserRepositoryImp userRepositoryImp;

    @Test
    public void findUser(){
        userRepositoryImp.getUserForSA();
    }
}
