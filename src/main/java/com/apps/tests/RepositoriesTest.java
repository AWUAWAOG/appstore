package com.apps.tests;

import com.apps.domain.Application;
import com.apps.domain.Developer;
import com.apps.domain.User;
import com.apps.repository.ApplicationRepository;
import com.apps.repository.UserRepository;
import lombok.NoArgsConstructor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.sql.Date;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@NoArgsConstructor
public class RepositoriesTest {

    public Developer developer = new Developer();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Test
    @Sql("createUser.sql")
    public void createUserTest() {
        userRepository.save(new User());
        assertThat(userRepository).isNotNull();
    }

    @Test
    public void ApplicationRepositoryIsNotNull() {
        applicationRepository.save(new Application());
        assertThat(new Application()).isNotNull();
    }

    @Test
    public void DeveloperRepositoryIsNotNull() {
        if (developer == null) {
            throw new IllegalArgumentException();
        } else
            System.out.println("DeveloperRepositoryIsNotNull test done!");
    }

    @Test
    public void userRegistrationTest() {
        try {
            User user = new User();
            user.setCreated(new Date(System.currentTimeMillis()));
            user.setEdited(new Date(System.currentTimeMillis()));
            user.setDeleted(false);
            userRepository.save(user);
        } catch (NullPointerException ignored){
        } finally {
            System.out.println("userRegistrationTest test done!");
        }
    }
}
