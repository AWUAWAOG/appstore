package com.apps.service;

import com.apps.domain.User;
import com.apps.domain.request.RegistrationRequest;
import com.apps.repository.UserRepository;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserRepository userRepository;
    private final String USER_ROLE = "USER";
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @SneakyThrows
    public ArrayList<User> getAllUsers() {
        return (ArrayList<User>) userRepository.findAll();
    }

    public User getUserById(int id) {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findById(id).orElse(new User());
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        user.setCreated(new Date(System.currentTimeMillis()));
        return userRepository.saveAndFlush(user);
    }

    @Transactional
    public void addAppToUser(int userId, int appId) {
        userRepository.addAppToUser(userId, appId);
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

    public boolean userRegistration(RegistrationRequest registrationRequest) {
        User user = new User();
        user.setUserLogin(registrationRequest.getUserLogin());
        user.setUserPassword(passwordEncoder.encode(registrationRequest.getUserPassword()));
        user.setEmail(registrationRequest.getEmail());
        user.setFirstName(registrationRequest.getFirstName());
        user.setLastName(registrationRequest.getLastName());
        user.setCreated(new Date(System.currentTimeMillis()));
        user.setEdited(new Date(System.currentTimeMillis()));
        user.setDeleted(false);
        user.setRole(USER_ROLE);

        logger.warn(user.toString());
        return userRepository.save(user) != null;
    }
}
