package com.apps.service;

import com.apps.domain.Application;
import com.apps.domain.User;
import com.apps.repository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;

@Service
public class UserService {

    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @SneakyThrows
    public ArrayList<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public User getUserById(int id) {
        return UserRepository.getUserById(id);
    }

    public boolean createUser(User user) throws SQLException {
        return userRepository.createUser(user);
    }

    public boolean updateUser(User user) {
        return userRepository.updateUser(user);
    }

    public boolean deleteUser(int id) {
        return userRepository.deleteUser(id);
    }

    public ArrayList<Application> getApplicationsForSingleUser(int id) throws SQLException {
        return userRepository.getApplicationsForSingleUser(id);
    }

    public boolean addApplicationToUser(int userId, int applicationId) {
        return userRepository.addApplicationToUser(userId, applicationId);
    }

    /*public boolean deleteApplicationFromUser(int userId, int applicationId) {
        return userRepository.deleteApplicationFromUser(userId, applicationId);
    }*/
}
