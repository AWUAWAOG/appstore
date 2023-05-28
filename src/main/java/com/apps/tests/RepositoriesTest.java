package com.apps.tests;

import com.apps.domain.Application;
import com.apps.domain.Developer;
import com.apps.domain.User;
import com.apps.repository.ApplicationRepository;
import com.apps.repository.DeveloperRepository;
import com.apps.repository.UserRepository;
import lombok.NoArgsConstructor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@NoArgsConstructor
public class RepositoriesTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private DeveloperRepository developerRepository;


    @Test
    @Sql("createUser.sql")
    public void createUserTest() {
        Optional<User> user = userRepository.findUserByUserLogin("LOGIN123");
        assertThat(user).isNotNull();
    }

    @Test
    public void ApplicationRepositoryIsNotNull(){
        applicationRepository.save(new Application());
        assertThat(applicationRepository).isNotNull();
    }

    @Test
    public void DeveloperRepositoryIsNotNull(){
        developerRepository.save(new Developer());
        assertThat(developerRepository).isNotNull();
    }
}
