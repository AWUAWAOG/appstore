package com.apps.service;

import com.apps.domain.User;
import com.apps.repository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final String USER_ROLE = "USER";
    private final PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @SneakyThrows
    public ArrayList<User> getAllUsers() {
        return (ArrayList<User>) userRepository.findAll();
    }

    /*public User getUserById(int id) {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        return UserRepository.findById(id).orElse(new User());
    }*/

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        user.setCreated(new Date(System.currentTimeMillis()));
        return userRepository.saveAndFlush(user);
    }

    @Transactional
    public void deleteUser(int id) {
        userRepository.deleteUser(id);
    }

    public Optional<User> findUserByUserLogin(String login) {
        return userRepository.findUserByUserLogin(login);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public Optional<User> findUserByRole(String role) {
        return userRepository.findUserByRole(role);
    }

/*public boolean userRegistration(UserRegistrationRequest userRegistrationRequest) { //TODO UserRegistrationRequest

    }*/
}
