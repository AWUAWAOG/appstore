package com.apps.repository;

import com.apps.domain.Application;
import com.apps.domain.User;
import com.apps.utils.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;

@Repository
public class UserRepository {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private Object User;

    public static JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ArrayList<User> getAllUsers() {
        return (ArrayList<User>) jdbcTemplate.query("SELECT * FROM users", new UserMapper());
    }

    public User getUserById(int id) {
       return jdbcTemplate.queryForObject("SELECT * FROM users WHERE id=?", new UserMapper(), id);
    }

    public boolean createUser(User user) throws SQLException {
        int result = jdbcTemplate.update("INSETR INTO users (id, user_login, user_password, email, first_name, last_name, created, edited, is_deleted)" +
                "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, DEFAULT)", new Object[]{user.getUser_login(), user.getUser_password(),user.getEmail(), user.getFirst_name(), user.getLast_name(),
                new Date((new java.util.Date()).getTime()), new Date((new java.util.Date()).getTime())});
        return result == 1;
    }

    public boolean updateUser(User user) {
        int result = jdbcTemplate.update("UPDATE users SET id=?, user_login=?, user_password=?, email=?, first_name=?, last_name=?, edited=?",
                new Object[]{user.getUser_login(), user.getUser_password(),user.getEmail(), user.getFirst_name(), user.getLast_name(), new Date((new java.util.Date()).getTime())});
        return result == 1;
    }

    public boolean deleteUser(int id) {
        int result = jdbcTemplate.update("UPDATE users SET is_deleted=true WGERE id=?", id);
        return result == 1;
    }

    public ArrayList<Application> getApplicationsForSingleUser(int id) throws SQLException {
        ArrayList<Application> applicationsList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/appstore_db", "postgres", "root")) {
            PreparedStatement statement = connection.prepareStatement("SELECT a.id, a.app_name, a.app_category, a.rating, a.description, a.app_year, a.price FROM l_applications_users JOIN applications as a ON l_applications_users.applications_id = a.id WHERE l_applications_users.user_id=?");
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Application application = new Application();
                application.setId(resultSet.getInt("id"));
                application.setApp_name(resultSet.getString("app_name"));
                application.setApp_category(resultSet.getString("app_category"));
                application.setRating(resultSet.getDouble("rating"));
                application.setDescription(resultSet.getString("description"));
                application.setApp_year(resultSet.getInt("app_year"));
                application.setPrice(resultSet.getDouble("price"));
                applicationsList.add(application);
            }
        } catch (SQLException e) {
            System.out.println("getApplicationsForSingleUser ERROR");
        }
        return applicationsList;
    }

    public boolean addApplicationToUser(int userId, int applicationId) {
        int result = jdbcTemplate.update("INSETRT INTO l_applications_users (id, applications_id, users_id)" +
                "VALUES (DEFAULT, ?, ?)", new Object[]{userId, applicationId});
        return result == 1;
    }

    /*public boolean deleteApplicationFromUser(int userId, int applicationId) {
        int result = jdbcTemplate.update("INSETRT INTO l_applications_users (id, applications_id, users_id)" +
                "VALUES (DEFAULT, ?, ?)", new Object[]{userId, applicationId});
        return result == 1;
    }*/
}
