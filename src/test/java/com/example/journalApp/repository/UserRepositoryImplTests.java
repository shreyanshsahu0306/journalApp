package com.example.journalApp.repository;

import com.example.journalApp.Repo.UserRepoImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryImplTests {

    @Autowired
    private UserRepoImpl userRepository;

    //@Disabled("tested")
    @Test
    void testSaveNewUser() {
        Assertions.assertNotNull(userRepository.getUsersForSA());
    }
}

