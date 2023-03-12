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

    public boolean createUser(String user_login, String user_password, String email, String first_name, String last_name, Date created, Date edited, Boolean is_deleted) throws SQLException {
        return userRepository.createUser(user_login, user_password, email, first_name, last_name);
    }

    public boolean updateUser(int id, String login, String password, String email, String first_name, String last_name, Boolean edited) {
        return userRepository.updateUser(id, login, password, email, first_name, last_name, edited);
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

    public boolean deleteApplicationFromUser(int userId, int applicationId) {
        return userRepository.deleteApplicationFromUser(userId, applicationId);
    }
}
