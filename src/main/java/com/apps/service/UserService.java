package com.apps.service;

import com.apps.domain.Users;

import java.sql.*;

public class UserService {
    {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Users getUserById(int id) {
        Users users = new Users();
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/appstore_db", "postgres", "root")) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id=?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();
            users.setId(resultSet.getInt("id"));
            users.setUser_login(resultSet.getString("user_login"));
            users.setUser_password(resultSet.getString("user_password"));
            users.setEmail(resultSet.getString("email"));
            users.setFirst_name(resultSet.getString("first_name"));
            users.setLast_name(resultSet.getString("last_name"));
            users.setCreated(resultSet.getDate("created"));
            users.setEdited(resultSet.getDate("edited"));
            users.set_deleted(resultSet.getBoolean("is_deleted"));
        } catch (SQLException e) {
            System.out.println("GET ERROR");
        }
        return users;
    }

    public boolean createUser(String user_login, String user_password, String email, String firstName, String lastName) throws SQLException {
        int result = 0;
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/appstore_db", "postgres", "root")) {

            PreparedStatement statement = connection.prepareStatement("INSERT INTO users (id, user_login, user_password, email, first_name, last_name, created, edited, is_deleted) " +
                    "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, DEFAULT)");
            statement.setString(1, user_login);
            statement.setString(2, user_password);
            statement.setString(3, email);
            statement.setString(4, firstName);
            statement.setString(5, lastName);
            statement.setDate(6, new Date((new java.util.Date()).getTime()));
            statement.setDate(7, new Date((new java.util.Date()).getTime()));

            result = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("CREATE ERROR");
        }
        return result == 1;
    }

    public boolean updateUser(int id, String login, String password, String email, String first_name, String last_name) {
        int result = 0;
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/appstore_db", "postgres", "root")) {

            PreparedStatement statement = connection.prepareStatement("UPDATE users SET user_login=?, user_password=?, email=?, first_name=?, last_name=?, edited=? WHERE id=?");
            statement.setString(1, login);
            statement.setString(2, password);
            statement.setString(3, email);
            statement.setString(4, first_name);
            statement.setString(5, last_name);
            statement.setDate(6, new Date((new java.util.Date()).getTime()));
            statement.setInt(7, id);

            result = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("UPDATE ERROR");
        }
        return result == 1;
    }

    public boolean deleteUser(int id) {
        int result = 0;
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/appstore_db", "postgres", "root")) {

            PreparedStatement statement = connection.prepareStatement("UPDATE users SET is_deleted=true WHERE id=?");
            statement.setInt(1, id);
            result = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("DELETE ERROR");
        }
        return result == 1;
    }
}